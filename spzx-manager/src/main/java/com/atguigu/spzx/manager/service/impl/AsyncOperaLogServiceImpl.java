package com.atguigu.spzx.manager.service.impl;

import com.atguigu.spzx.common.log.serivce.AsyncOperaLogService;
import com.atguigu.spzx.manager.mapper.AsyncOperaLogMapper;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author ：zxl
 * @Description: 日志处理
 * @ClassName: AsyncOperaLogServiceImpl
 * @date ：2024/04/28 19:18
 */
@Service
public class AsyncOperaLogServiceImpl implements AsyncOperaLogService {

    @Resource
    private AsyncOperaLogMapper asyncOperaLogMapper;

    /**
     * 保存日志数据
     * @param sysOperLog
     */
    @Override
    public void saveSysOperaLog(SysOperLog sysOperLog) {
        asyncOperaLogMapper.insert(sysOperLog);
    }
}
