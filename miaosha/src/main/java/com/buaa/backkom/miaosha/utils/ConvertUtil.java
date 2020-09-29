package com.buaa.backkom.miaosha.utils;

import com.buaa.backkom.miaosha.controller.viewobject.UserVO;
import com.buaa.backkom.miaosha.dataobject.UserDO;
import com.buaa.backkom.miaosha.dataobject.UserPasswordDO;
import com.buaa.backkom.miaosha.service.model.UserModel;
import org.springframework.beans.BeanUtils;

/**
 * @Author: backkom
 * @Date: 2020/9/18 20:43
 */
public class ConvertUtil
{
    public static UserVO convertUserModel2VO(UserModel userModel) {
        if(userModel == null){
            return null;
        }
        
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }
    
    public static UserPasswordDO convertPasswordFromModel(UserModel model) {
        if(model == null) {
            return null;
        }
        
        UserPasswordDO passwordDO = new UserPasswordDO();
        BeanUtils.copyProperties(model,passwordDO);
        return passwordDO;
    }
    
    public static UserDO convertDOFromModel(UserModel model) {
        if(model == null) {
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(model,userDO);
        return userDO;
    }
    
    public static UserModel convertUser2Model(UserDO userDO, UserPasswordDO userPasswordDO) {
        if(userDO == null){
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO,userModel);
        
        if(userPasswordDO == null){
            return null;
        }
        userModel.setEncryptPassword(userPasswordDO.getEncrptPassword());
        return userModel;
    }
}
