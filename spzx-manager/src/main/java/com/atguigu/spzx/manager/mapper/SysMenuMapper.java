package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.List;

public interface SysMenuMapper {
    List<SysMenu> findAll();

    void insert(SysMenu sysMenu);

    void updateById(SysMenu sysMenu);

    void removeById(Long id);

    int countChildById(Long id);

    List<SysMenu> findMenusByUserId(Long userId);

    SysMenu selectById(Long parentId);
}
