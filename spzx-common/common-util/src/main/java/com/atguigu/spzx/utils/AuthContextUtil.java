package com.atguigu.spzx.utils;

import com.atguigu.spzx.model.entity.system.SysUser;
import com.atguigu.spzx.model.entity.user.UserInfo;

/**
 * @author ：zxl
 * @Description: ThreadLocal工具类, 存储登录的用户信息，用于校验权限
 * @ClassName: AuthContextUtil
 * @date ：2024/04/16 15:03
 */
public class AuthContextUtil {
    private static final ThreadLocal<SysUser> threadLocal = new ThreadLocal<SysUser>();

    public static void set(SysUser sysUser) {
        threadLocal.set(sysUser);
    }

    public static SysUser get() {
        return threadLocal.get();
    }

    public static void remove() {
        threadLocal.remove();
    }

    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<UserInfo>();
    // 定义存储数据的静态方法
    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    // 定义获取数据的方法
    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get() ;
    }

    // 删除数据的方法
    public static void removeUserInfo() {
        userInfoThreadLocal.remove();
    }
}
