package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysRoleDto;
import com.atguigu.spzx.model.entity.system.SysRole;

import java.util.List;

public interface SysRoleMapper {

    List<SysRole> findByPage(SysRoleDto sysRoleDto);

    void savaSysRole(SysRole sysRole);

    void updateSysRoleById(SysRole sysRole);

    void deleteById(Long roleId);

    List<SysRole> findAllRoles();
}
