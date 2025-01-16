package com.atguigu.spzx.common.log.aspect;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.serivce.AsyncOperaLogService;
import com.atguigu.spzx.common.log.utils.LogUtil;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import jakarta.annotation.Resource;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author ：zxl
 * @Description: 日志切面
 * @ClassName: LogAspect
 * @date ：2024/04/28 14:59
 */
@Aspect
@Component
public class LogAspect {
    @Resource
    private AsyncOperaLogService asyncOperaLogService;
    //环绕通知
    @Around("@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint, Log sysLog) {

        //业务方法调用之前，封装数据
        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog,joinPoint,sysOperLog);
        //业务方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();
            //调用业务方法之后，封装数据
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,0,null);
        } catch (Throwable e) {
            LogUtil.afterHandlLog(sysLog,proceed,sysOperLog,1,e.getMessage());
            throw new RuntimeException(e);
        }
        //调用service方法把日志信息添加到数据库
        asyncOperaLogService.saveSysOperaLog(sysOperLog);
        return proceed;
    }
}
