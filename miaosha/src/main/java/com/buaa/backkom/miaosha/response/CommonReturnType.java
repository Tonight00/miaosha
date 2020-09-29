package com.buaa.backkom.miaosha.response;

import lombok.Data;

/**
 * @Author: backkom
 * @Date: 2020/9/18 14:46
 */
@Data
public class CommonReturnType
{
    private String status;
    private Object data;
    
    
    public static CommonReturnType create(Object data) {
        return create(data,"success");
    }
    
    public static CommonReturnType create(Object data,String status) {
        CommonReturnType type = new CommonReturnType();
        type.setData(data);
        type.setStatus(status);
        return type;
    }
}
