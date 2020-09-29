package com.buaa.backkom.miaosha.error;

/**
 * @Author: backkom
 * @Date: 2020/9/18 15:13
 */
public enum EmbusinessError implements CommonError {
    
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),
    
    //20000开头为用户信息相关错误定义
    NO_OPT_MESSAGE(20005,"验证码错误"),
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户手机号或密码不正确"),
    USER_NOT_LOGIN(20003,"用户还未登陆"),
    //30000开头为交易信息错误定义
    STOCK_NOT_ENOUGH(30001,"库存不足"),
    //40000开头为商品错误信息定义
    ITEM_NO_VALID(40001,"商品信息异常"),
    RATELIMIT(50001,"后端限速中");
    
    private int errCode;
    private String errMsg;
    
    EmbusinessError(int errCode,String errMsg) {
        this.errCode=errCode;
        this.errMsg=errMsg;
    }
    
    @Override
    public int getErrCode ()
    {
        return errCode;
    }
    
    @Override
    public String getErrMsg ()
    {
        return errMsg;
    }
    
    @Override
    public CommonError setErrMsg (String msg)
    {
        this.errMsg=msg;
        return this;
    }
}
