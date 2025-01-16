package com.atguigu.spzx.manager.service.impl;

import cn.hutool.db.Page;
import com.atguigu.spzx.manager.mapper.SysRoleMapper;
import com.atguigu.spzx.manager.mapper.SysRoleUserMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: SysRoleServiceImpl
 * @date ：2024/04/16 18:20
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;

    @Override
    public PageInfo<SysRole> findByPage(int current, int limit, SysRoleDto sysRoleDto) {
        //设置分页参数
        PageHelper.startPage(current,limit);
        //查询数据库
        List<SysRole> sysRoles = sysRoleMapper.findByPage(sysRoleDto);
        //封装pageInfo对象
        PageInfo<SysRole> sysRolesPageInfo = new PageInfo<SysRole>(sysRoles);
        return sysRolesPageInfo;
    }

    @Override
    public void savaSysRole(SysRole sysRole) {
        sysRoleMapper.savaSysRole(sysRole);
    }

    @Override
    public void updateSysRole(SysRole sysRole) {
        sysRoleMapper.updateSysRoleById(sysRole);
    }

    @Override
    public void deleteById(Long roleId) {
        sysRoleMapper.deleteById(roleId);
    }

    @Override
    public Map<String, Object> findAllRoles(Long userId) {
        //1.所有角色
        List<SysRole> roleList = sysRoleMapper.findAllRoles();
        HashMap<String, Object> allRolesMap = new HashMap<>();
        allRolesMap.put("allRoleList",roleList);
        //2.分配过的角色
        List<Long> roleIds = sysRoleUserMapper.findByUserId(userId);
        allRolesMap.put("roleIds",roleIds);
        return allRolesMap;
    }
}
