package com.atguigu.spzx.common.log.serivce;

import com.atguigu.spzx.model.entity.system.SysOperLog;

public interface AsyncOperaLogService {
    //保存日志数据
    void saveSysOperaLog(SysOperLog sysOperLog) ;
}
