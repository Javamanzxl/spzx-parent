package com.atguigu.spzx.user.mapper;

import com.atguigu.spzx.model.entity.user.UserInfo;

/**
 * @author ：zxl
 * @Description:
 * @ClassName: UserInfoMapper
 * @date ：2024/06/02 14:31
 */
public interface UserInfoMapper {
    int selectByUsername(String username);

    void save(UserInfo userInfo);

    UserInfo getUserByUsername(String username);
}
