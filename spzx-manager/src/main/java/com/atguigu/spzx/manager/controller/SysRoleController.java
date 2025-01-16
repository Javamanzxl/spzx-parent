package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ：zxl
 * @Description: 角色管理控制器
 * @ClassName: SysRoleController
 * @date ：2024/04/16 18:18
 */
@RestController
@RequestMapping("/admin/system/sysRole")
@Slf4j
public class SysRoleController {
    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 分页查询用户角色
     * @param current
     * @param limit
     * @param sysRoleDto
     * @return
     */
    //current 当前页  limit 每页显示的记录数
    @GetMapping("/findByPage/{current}/{limit}")
    public Result<PageInfo<SysRole>> findByPage(@PathVariable int current, @PathVariable int limit, SysRoleDto sysRoleDto){
        PageInfo<SysRole> sysRolePageInfo = sysRoleService.findByPage(current,limit,sysRoleDto);
        return Result.build(sysRolePageInfo, ResultCodeEnum.SUCCESS);
    }

    /**
     * 添加角色
     * @param sysRole
     * @return
     */
    @PostMapping("/savaSysRole")
    public Result savaSysRole(@RequestBody SysRole sysRole){
        sysRoleService.savaSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 更新
     * @param sysRole
     * @return
     */

    @PutMapping("/updateSysRole")
    public Result updateSysRole(@RequestBody SysRole sysRole){
        sysRoleService.updateSysRole(sysRole);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 根据id删除角色
     * @param roleId
     * @return
     */
    @DeleteMapping("/deleteById/{roleId}")
    public Result deleteById(@PathVariable("roleId") Long roleId){
        sysRoleService.deleteById(roleId);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 查询所有角色包括用户分配过的角色
     * Map第一份数据是所有角色，第二份是用户分配过的角色
     * @return
     */
    @GetMapping("/findAllRoles/{userId}")
    public Result<Map<String,Object>> findAllRoles(@PathVariable Long userId){
        Map<String,Object> allRoles = sysRoleService.findAllRoles(userId);
        return Result.build(allRoles,ResultCodeEnum.SUCCESS);
    }

}
