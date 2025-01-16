package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.atguigu.spzx.common.exception.MyException;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.manager.utils.BuildSysMenuVo;
import com.atguigu.spzx.manager.utils.MenuHelper;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: SysMenuServiceImpl
 * @date ：2024/04/21 16:14
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {
    @Resource
    private SysMenuMapper sysMenuMapper;
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        //1.查询所有菜单，返回list集合
        List<SysMenu> sysMenuList = sysMenuMapper.findAll();
        //2.调用工具类的方法，把返回list集合封装要求数据格式
        if (!CollectionUtil.isEmpty(sysMenuList)) {
            return MenuHelper.buildTree(sysMenuList);
        }
        return null;
    }

    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.insert(sysMenu);
        //新添加子菜单，把父菜单isHalf半开状态改为1
        updateSysRoleMenuIsHalf(sysMenu);
    }

    /**
     * 新添加子菜单，把父菜单isHalf半开状态改为1
     *
     * @param sysMenu
     */

    private void updateSysRoleMenuIsHalf(SysMenu sysMenu) {
        // 查询是否存在父节点
        SysMenu parentMenu = sysMenuMapper.selectById(sysMenu.getParentId());

        if (parentMenu != null) {
            // 将该id的菜单设置为半开
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());
            // 递归调用
            if(parentMenu.getId().intValue() != 0){
                updateSysRoleMenuIsHalf(parentMenu);
            }

        }

    }

    @Override
    public void updateById(SysMenu sysMenu) {
        sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public void removeById(Long id) {
        //判断有没有子菜单,存在不允许删除
        int count = sysMenuMapper.countChildById(id);
        if (count > 0) {
            throw new MyException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.removeById(id);
    }

    @Override
    public List<SysMenuVo> findMenusByUserId(Long userId) {
        //根据用户id查询role_user表的roleId,根据roleId查询role_menu表的menu_id，再查找sys_menu表
        List<SysMenu> sysMenuList = sysMenuMapper.findMenusByUserId(userId);
        //封装数据
        sysMenuList = MenuHelper.buildTree(sysMenuList);
        return BuildSysMenuVo.build(sysMenuList);
    }
}
