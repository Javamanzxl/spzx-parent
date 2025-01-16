package com.atguigu.spzx.manager.controller;

import cn.hutool.http.server.HttpServerRequest;
import com.atguigu.spzx.manager.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.manager.service.ValidateCodeService;
import com.atguigu.spzx.model.dto.system.LoginDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.system.LoginVo;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import com.atguigu.spzx.model.vo.system.ValidateCodeVo;
import com.atguigu.spzx.utils.AuthContextUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;

/**
 * @author ：zxl
 * @Description: 登录页面
 * @ClassName: IndexController
 * @date ：2024/04/10 19:37
 */
@Tag(name="用户接口")
@RestController
@RequestMapping("/admin/system/index")
public class IndexController {
    @Resource
    private SysUserService sysUserService;
    @Resource
    private ValidateCodeService validateCodeService;
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 用户登录
     * @param loginDto
     * @return
     */
    @Operation(summary = "登录的方法")
    @PostMapping("login")
    public Result<LoginVo> login(@RequestBody LoginDto loginDto){
        LoginVo vo = sysUserService.login(loginDto);
        return Result.build(vo, ResultCodeEnum.SUCCESS);
    }


    /**
     * 生成验证码
     * @return
     */
    @GetMapping(value = "/generateValidateCode")
    public Result<ValidateCodeVo> generateValidateCode(){
       ValidateCodeVo validateCodeVo = validateCodeService.generateValidateCode();
       return Result.build(validateCodeVo,ResultCodeEnum.SUCCESS);
    }

//    /**
//     * 获取用户信息(没加ThreadLocal)
//     * @param request
//     * @return
//     */
//    @GetMapping("/getUserInfo")
//    public Result<SysUser> getUserInfo(HttpServletRequest request){
//        //1.从请求头中获取token
//        String token = request.getHeader("token");
//        //2.根据token查询redis获取用户信息
//        SysUser sysUser = sysUserService.getUserInfo(token);
//        //3.用户信息返回
//        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
//    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/getUserInfo")
    public Result<SysUser> getUserInfo(){
        SysUser sysUser = AuthContextUtil.get();
        return Result.build(sysUser,ResultCodeEnum.SUCCESS);
    }

    /**
     * 用户退出
     * @param request
     * @return
     */
    @GetMapping("logout")
    public Result logout(HttpServletRequest request){
        String token = request.getHeader("token");
        sysUserService.logout(token);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 动态菜单
     * @return
     */
    @GetMapping("/menus")
    public Result menus(){
        Long userId = AuthContextUtil.get().getId();
        List<SysMenuVo> sysMenuVoList = sysMenuService.findMenusByUserId(userId);
        return Result.build(sysMenuVoList,ResultCodeEnum.SUCCESS);

    }
}
