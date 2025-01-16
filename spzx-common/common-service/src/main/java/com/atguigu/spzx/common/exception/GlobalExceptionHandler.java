package com.atguigu.spzx.common.exception;

import cn.hutool.core.util.StrUtil;
import com.atguigu.spzx.model.vo.common.Result;
import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ：zxl
 * @Description: 全局统一异常处理类
 * @ClassName: GlobalExceptionHandler
 * @date ：2024/04/11 21:17
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        String message = e.getMessage();
        log.info(e.getMessage());
        if(StrUtil.equals(message,"Maximum upload size exceeded")){
            log.info("图片过大");
            return Result.build(null,ResultCodeEnum.PHOTO_SIZE_OVER);
        }
        return Result.build(null, ResultCodeEnum.SYSTEM_ERROR);
    }
    //自定义异常处理
    @ExceptionHandler(MyException.class)
    public Result error(MyException e){
        log.info(e.getMessage());
        return Result.build(null,e.getResultCodeEnum());
    }
}
