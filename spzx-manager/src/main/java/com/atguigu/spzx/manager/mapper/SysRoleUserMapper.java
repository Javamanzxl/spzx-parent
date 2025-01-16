package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.AssginRoleDto;

import java.util.List;

public interface SysRoleUserMapper {
    void deleteByUserId(Long userId);

    void doAssign(Long userId,Long roleId);

    List<Long> findByUserId(Long userId);
}
