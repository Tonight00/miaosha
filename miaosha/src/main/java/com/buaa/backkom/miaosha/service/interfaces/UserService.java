package com.buaa.backkom.miaosha.service.interfaces;

import com.buaa.backkom.miaosha.error.BusinessException;
import com.buaa.backkom.miaosha.service.model.UserModel;

/**
 * @Author: backkom
 * @Date: 2020/9/18 12:28
 */
public interface UserService
{
    UserModel getUserById(Integer id);
    void register(UserModel userModel) throws BusinessException;
    UserModel validateLogin(String telephone, String password) throws BusinessException;
}
