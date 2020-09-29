package com.buaa.backkom.miaosha.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: backkom
 * @Date: 2020/9/19 10:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemModel implements Serializable
{
    
    private static final long serialVersionUID = -6212419458800219738L;
    private Integer id;
    //商品名称
    @NotBlank
    private String title;
    //商品价格
    @NotNull
    private BigDecimal price;
    //商品的库存
    @NotNull
    private Integer stock;
    //商品的描述
    @NotBlank
    private String description;
    //商品描述图片的URL
    @NotBlank
    private String imgUrl;
    //商品的销量
    private Integer sales;
}