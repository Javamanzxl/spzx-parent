package com.atguigu.spzx.common.exception;

import com.atguigu.spzx.model.vo.common.ResultCodeEnum;
import lombok.Data;

/**
 * @author ：zxl
 * @Description: 自定义异常
 * @ClassName: MyException
 * @date ：2024/04/11 21:21
 */
@Data
public class MyException extends RuntimeException {
    private Integer code;
    private String message;
    private ResultCodeEnum resultCodeEnum;

    public MyException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum = resultCodeEnum;
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
}
