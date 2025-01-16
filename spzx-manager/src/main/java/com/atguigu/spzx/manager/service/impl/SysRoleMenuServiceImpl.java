package com.atguigu.spzx.manager.service.impl;

import cn.hutool.core.collection.ListUtil;
import com.atguigu.spzx.manager.mapper.SysMenuMapper;
import com.atguigu.spzx.manager.mapper.SysRoleMenuMapper;
import com.atguigu.spzx.manager.service.SysMenuService;
import com.atguigu.spzx.manager.service.SysRoleMenuService;
import com.atguigu.spzx.model.dto.system.AssginMenuDto;
import com.atguigu.spzx.model.entity.system.SysMenu;
import com.atguigu.spzx.model.vo.system.SysMenuVo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: SysRoleMenuServiceImpl
 * @date ：2024/04/22 14:42
 */
@Service
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;
    @Resource
    private SysMenuService sysMenuService;

    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        List<SysMenu> sysMenuList = sysMenuService.findNodes();
        List<Long> roleMenuIds = sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);
        HashMap<String, Object> result = new HashMap<>();
        result.put("sysMenuList",sysMenuList);
        result.put("roleMenuIds",roleMenuIds);
        return result;
    }

    @Override
    public void doAssign(AssginMenuDto assginMenuDto) {
        sysRoleMenuMapper.deleteByRoleId(assginMenuDto.getRoleId());
        List<Map<String, Number>> menuInfo = assginMenuDto.getMenuIdList();
        if(!menuInfo.isEmpty()){
            sysRoleMenuMapper.doAssign(assginMenuDto);
        }

    }
}
