package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Map;

/**
 * @author ：zxl
 * @Description: 菜单角色表
 * @ClassName: SysRoleMenuController
 * @date ：2024/04/22 14:45
 */
@RestController
@RequestMapping("/admin/system/sysRoleMenu")
public class SysRoleMenuController {
    @Resource
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 查询所有菜单或根据角色id查询
     * @param roleId
     * @return
     */
    @GetMapping("/findSysRoleMenuByRoleId/{roleId}")
    public Result<Map<String,Object>> findSysRoleMenuByRoleId(@PathVariable("roleId")Long roleId){
        Map<String,Object> sysMenuList = sysRoleMenuService.findSysRoleMenuByRoleId(roleId);
        return Result.build(sysMenuList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 为角色分配菜单
     * @param assginMenuDto
     * @return
     */
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssginMenuDto assginMenuDto){
        sysRoleMenuService.doAssign(assginMenuDto);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
