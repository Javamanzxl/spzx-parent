package com.atguigu.spzx.manager.utils;

import com.atguigu.spzx.model.entity.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：zxl
 * @Description: 菜单数据封装工具
 * @ClassName: MenuHelper
 * @date ：2024/04/21 16:37
 */
public class MenuHelper {
    /**
     * 通过递归实现封装过程
     * @param sysMenuList
     * @return
     */
    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList){
        //sysMenuList所有菜单集合
        //创建一个list集合，用于封装最终的数据
        List<SysMenu> trees = new ArrayList<>();
        //遍历所有菜单集合
        for(SysMenu sysMenu:sysMenuList){
            //找到第一层菜单 parentId = 0
            if(sysMenu.getParentId()==0){
                //根据第一层，找下层数据
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        return trees;
    }

    /**
     * 递归查找下层菜单
     * @param sysMenu
     * @param sysMenuList
     * @return
     */
    private static SysMenu findChildren(SysMenu sysMenu, List<SysMenu> sysMenuList) {
        //对sysMenu的List<SysMenu> children属性初始化
        sysMenu.setChildren(new ArrayList<SysMenu>());
        //把parentId值等于sysMenu.id的值的数据放入sysMenu.child中
        for(SysMenu menu:sysMenuList){
            if(menu.getParentId().longValue() == sysMenu.getId().longValue()){
                sysMenu.getChildren().add(findChildren(menu,sysMenuList));
            }
        }
        return sysMenu;
    }
}
