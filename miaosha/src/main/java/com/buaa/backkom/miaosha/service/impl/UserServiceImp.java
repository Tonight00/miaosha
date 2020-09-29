package com.buaa.backkom.miaosha.service.impl;

import com.buaa.backkom.miaosha.dao.UserDOMapper;
import com.buaa.backkom.miaosha.dao.UserPasswordDOMapper;
import com.buaa.backkom.miaosha.dataobject.UserDO;
import com.buaa.backkom.miaosha.dataobject.UserPasswordDO;
import com.buaa.backkom.miaosha.error.BusinessException;
import com.buaa.backkom.miaosha.error.EmbusinessError;
import com.buaa.backkom.miaosha.service.interfaces.UserService;
import com.buaa.backkom.miaosha.service.model.UserModel;
import com.buaa.backkom.miaosha.utils.ConvertUtil;
import com.buaa.backkom.miaosha.validator.ValidatorImp;
import com.buaa.backkom.miaosha.validator.ValidatorResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: backkom
 * @Date: 2020/9/18 12:29
 */
@Service
public class UserServiceImp implements UserService
{
    @Autowired
    private UserDOMapper userDOMapper;
    
    @Autowired
    private UserPasswordDOMapper userPasswordDOMapper;
    
    @Autowired
    private ValidatorImp validator;
    
    @Override
    public UserModel getUserById (Integer id) {
       UserDO userDO= userDOMapper.selectById(id);
       if(userDO == null){
           return null;
       }
       
       UserPasswordDO userPasswordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
       
       return ConvertUtil.convertUser2Model(userDO,userPasswordDO);
    }
    
    @Override
    @Transactional
    public void register (UserModel userModel) throws BusinessException
    {
        if(userModel == null) {
            throw new BusinessException(EmbusinessError.PARAMETER_VALIDATION_ERROR,"用户注册信息不全");
        }
        ValidatorResult result =  validator.validate(userModel);
        if(result.getHasErros()) {
            throw new BusinessException(EmbusinessError.PARAMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        
        UserDO userDO =ConvertUtil.convertDOFromModel(userModel);
        try {
            userDOMapper.insertSelective(userDO);
        } catch (DuplicateKeyException ex) {
            throw new BusinessException(EmbusinessError.PARAMETER_VALIDATION_ERROR,"重复注册");
        }
        
        UserPasswordDO userPasswordDO = ConvertUtil.convertPasswordFromModel(userModel);
        userPasswordDO.setUserId(userDO.getId());
        userPasswordDO.setEncrptPassword(userModel.getEncryptPassword());
        userPasswordDOMapper.insertSelective(userPasswordDO);
    }
    
    @Override
    public UserModel validateLogin(String telephone, String password) throws BusinessException
    {
        UserDO userDO= userDOMapper.selectBytelephone(telephone);
        if(userDO == null) {
            throw new BusinessException(EmbusinessError.USER_LOGIN_FAIL);
        }
        UserPasswordDO passwordDO = userPasswordDOMapper.selectByUserId(userDO.getId());
        if(!passwordDO.getEncrptPassword().equals(password)) {
            throw new BusinessException(EmbusinessError.USER_LOGIN_FAIL);
        }
        return ConvertUtil.convertUser2Model(userDO,passwordDO);
    }
    
}
