package com.buaa.backkom.miaosha.controller.viewobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: backkom
 * @Date: 2020/9/18 13:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO
{
    private Integer id;
    private String name;
    private Byte gender;
    private Integer age;
    private String telephone;
}
