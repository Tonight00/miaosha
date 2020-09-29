package com.buaa.backkom.miaosha.error;

/**
 * @Author: backkom
 * @Date: 2020/9/18 15:11
 */
public interface CommonError {
    public int getErrCode();
    public String getErrMsg();
    public CommonError setErrMsg(String msg);
}
