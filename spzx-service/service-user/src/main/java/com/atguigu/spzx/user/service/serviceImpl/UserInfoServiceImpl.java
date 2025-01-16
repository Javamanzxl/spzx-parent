package com.atguigu.spzx.user.service.serviceImpl;

import com.alibaba.fastjson2.JSON;
import com.atguigu.spzx.common.exception.MyException;
import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.user.UserInfo;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;
import com.atguigu.spzx.user.mapper.UserInfoMapper;
import com.atguigu.spzx.user.service.UserInfoService;
import com.atguigu.spzx.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: UserInfoServiceImpl
 * @date ：2024/06/02 14:30
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public void register(UserRegisterDto userRegisterDto) {
        String username = userRegisterDto.getUsername();
        String nickName = userRegisterDto.getNickName();
        String code = userRegisterDto.getCode();
        String password = userRegisterDto.getPassword();
        //校验信息不为空
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(nickName)||StringUtils.isEmpty(code)||StringUtils.isEmpty(password)){
            throw new MyException(ResultCodeEnum.DATA_ERROR);
        }
        //校验用户名唯一
        int count = userInfoMapper.selectByUsername(username);
        if(count > 0 ){
            throw new MyException(ResultCodeEnum.USER_NAME_IS_EXISTS);
        }
        //校验验证码正确
        String redisCode = redisTemplate.opsForValue().get("phone:code"+username);
        if(!code.equals(redisCode)){
            throw new MyException(ResultCodeEnum.VALIDATECODE_ERROR);
        }
        //保存用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setNickName(nickName);
        userInfo.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
        userInfo.setPhone(username);
        userInfo.setStatus(1);
        userInfo.setSex(0);
        userInfo.setAvatar("http://img.touxiangwu.com/zb_users/upload/2023/04/202304061680747835345417.jpg");
        userInfoMapper.save(userInfo);
        //从redis中删除信息
        redisTemplate.delete("phone:code"+username);
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        String username = userLoginDto.getUsername();
        String password = userLoginDto.getPassword();
        if(StringUtils.isEmpty(username)||StringUtils.isEmpty(password)){
            throw new MyException(ResultCodeEnum.DATA_ERROR);
        }
        UserInfo userInfo = userInfoMapper.getUserByUsername(username);
        if(userInfo == null){
            throw new MyException(ResultCodeEnum.USER_NOEXIT);
        }
        //校验密码
        String MD5password = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!MD5password.equals(userInfo.getPassword())){
            throw  new MyException(ResultCodeEnum.LOGIN_ERROR);
        }
        //校验是否被禁用
        if(userInfo.getStatus() == 0) {
            throw new MyException(ResultCodeEnum.ACCOUNT_STOP);
        }
        String token = UUID.randomUUID().toString().replaceAll("-", "");
        redisTemplate.opsForValue().set("user:"+token, JSON.toJSONString(userInfo), 30, TimeUnit.MINUTES);
        return token;
    }

    @Override
    public UserInfoVo getCurrentUserInfo(String token) {
        String userInfoJson = redisTemplate.opsForValue().get("user:" + token);
        UserInfo userInfo = JSON.parseObject(userInfoJson, UserInfo.class);
        if(userInfo == null){
            throw new MyException(ResultCodeEnum.LOGIN_AUTH);
        }
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(userInfo,userInfoVo);
        return userInfoVo;
    }

    @Override
    public void logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        redisTemplate.delete("user:"+token);
    }
}
