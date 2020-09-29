package com.buaa.backkom.miaosha.validator;

import lombok.Data;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: backkom
 * @Date: 2020/9/18 21:32
 */
@Data
public class ValidatorResult
{
    private Boolean HasErros = false;
    private Map<String,String> errorMap = new HashMap<>();
    
    public String getErrMsg() {
        return StringUtils.join(errorMap.values(),",");
    }
}
