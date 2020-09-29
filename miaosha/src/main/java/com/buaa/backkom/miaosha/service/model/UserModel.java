package com.buaa.backkom.miaosha.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: backkom
 * @Date: 2020/9/18 12:33
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserModel implements Serializable
{
    private static final long serialVersionUID = -2766626638075667160L;
    private Integer id;
    @NotBlank(message = "用户名不能为空")
    private String name;
    @NotNull(message = "性别不能不填")
    private Byte gender;
    @Min(value = 0, message = "年龄不能为0")
    @Max(value = 120, message = "年龄不能超过120岁")
    private Integer age;
    @NotBlank(message = "手机号不能为空")
    private String telephone;
    private String registerMode;
    private String thirdPartyId;
    private String encryptPassword;
}
