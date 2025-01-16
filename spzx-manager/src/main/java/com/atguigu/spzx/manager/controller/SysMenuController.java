package com.atguigu.spzx.manager.controller;

import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ：zxl
 * @Description: 菜单管理
 * @ClassName: SysMenuController
 * @date ：2024/04/21 16:05
 */
@RestController
@RequestMapping("/admin/system/sysMenu")
@Slf4j
public class SysMenuController {
    @Resource
    private SysMenuService sysMenuService;

    /**
     * 菜单列表查询
     * @return
     */
    @GetMapping("findNodes")
    public Result<List<SysMenu>> findNodes(){
        List<SysMenu> sysMenuList = sysMenuService.findNodes();
        return Result.build(sysMenuList, ResultCodeEnum.SUCCESS);
    }

    /**
     * 新增菜单
     * @param sysMenu
     * @return
     */
    @PostMapping("/save")
    public Result save(@RequestBody SysMenu sysMenu){
        sysMenuService.save(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }

    /**
     * 更新
     * @param sysMenu
     * @return
     */
    @PutMapping("updateById")
    public Result updateById(@RequestBody SysMenu sysMenu){
        sysMenuService.updateById(sysMenu);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
    @DeleteMapping("removeById/{id}")
    public Result removeById(@PathVariable("id")Long id){
        sysMenuService.removeById(id);
        return Result.build(null,ResultCodeEnum.SUCCESS);
    }
}
