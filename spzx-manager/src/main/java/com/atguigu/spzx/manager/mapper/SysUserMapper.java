package com.atguigu.spzx.manager.mapper;

import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;

import java.util.List;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: SysUserMapper
 * @date ：2024/04/10 19:40
 */
public interface SysUserMapper {
    SysUser selectUserInfoByUserName(String userName);

    List<SysUser> findByPage(SysUserDto sysUserDto);

    void saveSysUser(SysUser sysUser);

    void updateSysUser(SysUser sysUser);

    void deleteById(Long userId);
}
