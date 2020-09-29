package com.buaa.backkom.miaosha.error;

/**
 * @Author: backkom
 * @Date: 2020/9/18 15:21
 */
public class BusinessException extends  Exception implements CommonError
{
    private CommonError commonError;
    
    public BusinessException(CommonError error) {
        super();
        commonError=error;
    }
    
    public BusinessException(CommonError error,String msg) {
        super();
        commonError=error;
        commonError.setErrMsg(msg);
    }
    @Override
    public int getErrCode ()
    {
        return commonError.getErrCode();
    }
    
    @Override
    public String getErrMsg ()
    {
        return commonError.getErrMsg();
    }
    
    @Override
    public CommonError setErrMsg (String msg)
    {
        return commonError.setErrMsg(msg);
    }
}
