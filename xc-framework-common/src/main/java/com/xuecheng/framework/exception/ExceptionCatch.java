package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//统一捕获异常
@ControllerAdvice
public class ExceptionCatch {


    private static final Logger LOGGER= LoggerFactory.getLogger(ExceptionCatch.class);

    //使用EXCEPTIOINS存放异常类型和错误代码的映射，ImmutableMap的特点一旦创建不可改变
    private static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;
    //使用build来构建一个异常类型和错误代码的异常
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder=
            ImmutableMap.builder();

    //1、捕获Exception不可预知异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e) {
        e.printStackTrace();
        LOGGER.error("catch exception : {}", e.getMessage());
        if(EXCEPTIONS ==null) {
            EXCEPTIONS = builder.build();
        }
        final ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        final ResponseResult responseResult;
        if(resultCode !=null) {
            responseResult = new ResponseResult(resultCode);
        }else {
            responseResult = new ResponseResult(CommonCode.SERVER_ERROR);
        }
        return responseResult;
    }

    static {
        //添加指定异常类型
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
        //往下添加
    }



    //2、捕获CustomException可预知异常
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e){
        e.printStackTrace();
        LOGGER.error("catch exception：{}\r\nexception:",e.getMessage(),e);
        ResultCode resultCode = e.getResultCode();
        ResponseResult responseResult = new ResponseResult(resultCode);
        return responseResult;
    }
}
