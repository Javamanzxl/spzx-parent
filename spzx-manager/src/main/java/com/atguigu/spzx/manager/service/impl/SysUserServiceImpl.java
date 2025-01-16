package com.atguigu.spzx.manager.service.impl;


import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.spzx.common.exception.MyException;
import com.atguigu.spzx.manager.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.bouncycastle.crypto.digests.MD5Digest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: SysUserServiceImpl
 * @date ：2024/04/10 19:39
 */
@Service
public class SysUserServiceImpl implements SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public LoginVo login(LoginDto loginDto) {
        //1.验证码校验
        String codeKey = loginDto.getCodeKey(); //获得验证码的key
        String captcha= loginDto.getCaptcha();  //获得用户属于的验证码
        String codeValue = redisTemplate.opsForValue().get("user:validate" + codeKey); //获取redis存储的正确的key
        if(StrUtil.isEmpty(codeValue) || !StrUtil.equalsIgnoreCase(captcha,codeValue)){
            throw new MyException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        redisTemplate.delete(codeKey);
        //2.获取提交的用户名
        String userName = loginDto.getUserName();
        //3.根据用户名查询sys_user
        SysUser sysUser = sysUserMapper.selectUserInfoByUserName(userName);
        //4.如果没有对应信息，用户不存在，返回错误信息
        if(sysUser == null){
            throw new MyException(ResultCodeEnum.LOGIN_ERROR);
        }
        //5.如果查询到用户信息，用户存在
        //6.获取输入的密码，比较输入密码和数据库的密码是否一致
            //对loginDto中的密码进行MD5加密，再与数据库中的密码比较
        String input_password = DigestUtils.md5DigestAsHex(loginDto.getPassword().getBytes(StandardCharsets.UTF_8));
        if(!input_password.equals(sysUser.getPassword())){
            throw new MyException(ResultCodeEnum.LOGIN_ERROR);
        }
        //7.如果密码一致，登录成功，不一致失败
        //8.生成token字符串
        String token = UUID.randomUUID().toString().replaceAll("-","");
        //9.把登录成功的用户信息转为json字符串放入redis里
        redisTemplate.opsForValue().set("user:login"+token,JSON.toJSONString(sysUser),7, TimeUnit.DAYS);
        //10.返回loginVo
        LoginVo loginVo = new LoginVo();
        loginVo.setToken(token);
        return loginVo;
    }

    @Override
    public SysUser getUserInfo(String token) {
        String userJson = redisTemplate.opsForValue().get("user:login" + token);
        return JSON.parseObject(userJson, SysUser.class);
    }

    @Override
    public void logout(String token) {
        redisTemplate.delete("user:login"+token);
    }

    @Override
    public PageInfo<SysUser> findByPage(Integer pageNum, Integer pageSize, SysUserDto sysUserDto) {
        PageHelper.startPage(pageNum,pageSize);
        List<SysUser> sysUsers =  sysUserMapper.findByPage(sysUserDto);
        PageInfo<SysUser> sysUserPageInfo = new PageInfo<>(sysUsers);
        return sysUserPageInfo;
    }

    @Override
    public void saveSysUser(SysUser sysUser) {
        //1.判断用户名不能重复
        String userName = sysUser.getUserName();
        SysUser dbSysUser = sysUserMapper.selectUserInfoByUserName(userName);
        if(dbSysUser != null){
            throw new MyException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        String passwordMD5 = DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes(StandardCharsets.UTF_8));
        sysUser.setPassword(passwordMD5);
        sysUser.setStatus(1);
        sysUserMapper.saveSysUser(sysUser);
    }

    @Override
    public void updateSysUser(SysUser sysUser) {
        String password = sysUser.getPassword();
        if(!StrUtil.isEmpty(password)){
            String passwordMD5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
            sysUser.setPassword(passwordMD5);
        }
        sysUserMapper.updateSysUser(sysUser);
    }

    @Override
    public void deleteById(Long userId) {
        sysUserMapper.deleteById(userId);
    }

    @Override
    public void doAssign(AssginRoleDto assginRoleDto) {

        //1.根据userId删除用户之前分配角色数据
        sysRoleUserMapper.deleteByUserId(assginRoleDto.getUserId());
        //2.重新分配新数据
        List<Long> roleIdList = assginRoleDto.getRoleIdList();
        for(Long roleId:roleIdList){
            sysRoleUserMapper.doAssign(assginRoleDto.getUserId(),roleId);
        }

    }
}
