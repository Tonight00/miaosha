package com.buaa.backkom.miaosha.controller;

import com.buaa.backkom.miaosha.error.BusinessException;
import com.buaa.backkom.miaosha.error.EmbusinessError;
import com.buaa.backkom.miaosha.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: backkom
 * @Date: 2020/9/19 19:12
 */
@ControllerAdvice
@Component
public class GlobalController
{
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonReturnType doError(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Exception ex) {
        ex.printStackTrace();
        Map<String,Object> responseData = new HashMap<>();
        if( ex instanceof BusinessException){
            BusinessException businessException = (BusinessException)ex;
            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());
        }else if(ex instanceof ServletRequestBindingException){
            responseData.put("errCode",EmbusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg","url绑定路由问题");
        }else if(ex instanceof NoHandlerFoundException){
            responseData.put("errCode",EmbusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg","没有找到对应的访问路径");
        }else{
            responseData.put("errCode", EmbusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg",EmbusinessError.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData,"fail");
    }
}
