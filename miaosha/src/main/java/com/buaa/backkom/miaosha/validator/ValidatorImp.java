package com.buaa.backkom.miaosha.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;


/**
 * @Author: backkom
 * @Date: 2020/9/18 21:36
 */
@Component
public class ValidatorImp implements InitializingBean
{
    private Validator validator;
    
    //实现校验方法并返回校验结果
    public ValidatorResult validate(Object bean) {
        ValidatorResult result = new ValidatorResult();
        Set<ConstraintViolation<Object>> constraintViolations =validator.validate(bean);
        if(constraintViolations.size() > 0) {
            result.setHasErros(true);
            constraintViolations.forEach(constraintViolation->{
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrorMap().put(propertyName,errMsg);
            });
        }
        return result;
    }
    
    
    @Override
    public void afterPropertiesSet () throws Exception
    {
        validator =  Validation.buildDefaultValidatorFactory().getValidator();
        
    }
}
