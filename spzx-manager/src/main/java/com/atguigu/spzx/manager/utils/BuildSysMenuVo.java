package com.atguigu.spzx.manager.utils;

import cn.hutool.core.collection.CollectionUtil;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：zxl
 * @Description: 封装SysMenuVo数据类型
 * @ClassName: BuidSysMenuVo
 * @date ：2024/04/23 20:01
 */
public class BuildSysMenuVo {
    public static List<SysMenuVo> build(List<SysMenu> sysMenuList){
        List<SysMenuVo> sysMenuVos = new ArrayList<>();
        for(SysMenu sysMenu:sysMenuList){
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setName(sysMenu.getComponent());
            sysMenuVo.setTitle(sysMenu.getTitle());
            if(!CollectionUtil.isEmpty(sysMenu.getChildren())) {
                sysMenuVo.setChildren(build(sysMenu.getChildren()));
            }
            sysMenuVos.add(sysMenuVo);
        }
        return sysMenuVos;
    }
}
