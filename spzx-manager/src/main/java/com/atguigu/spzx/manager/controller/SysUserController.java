package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @author ：zxl
 * @Description: 用户管理
 * @ClassName: SysUserController
 * @date ：2024/04/17 16:16
 */
@RestController
@RequestMapping("admin/system/sysUser")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    /**
     * 用户信息分页查询
     * @param sysUserDto
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/findByPage/{pageNum}/{pageSize}")
    public Result<PageInfo<SysUser>> findByPage(SysUserDto sysUserDto ,
                                                @PathVariable(value = "pageNum") Integer pageNum ,
                                                @PathVariable(value = "pageSize") Integer pageSize){
        PageInfo<SysUser> sysUserPageInfo = sysUserService.findByPage(pageNum,pageSize,sysUserDto);
        return Result.build(sysUserPageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 新增用户
     * @param sysUser
     * @return
     */
    @PostMapping("/saveSysUser")
    public Result saveSysUser(@RequestBody SysUser sysUser){
        sysUserService.saveSysUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @PutMapping("/updateSysUser")
    public Result updateSysUser(@RequestBody SysUser sysUser){
        sysUserService.updateSysUser(sysUser);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @DeleteMapping(value = "/deleteById/{userId}")
    public Result deleteById(@PathVariable("userId") Long userId){
        sysUserService.deleteById(userId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginRoleDto assginRoleDto){
        sysUserService.doAssign(assginRoleDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

}
