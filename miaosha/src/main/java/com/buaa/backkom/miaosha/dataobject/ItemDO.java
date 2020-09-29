package com.buaa.backkom.miaosha.dataobject;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ToString
public class ItemDO implements Serializable
{
    private static final long serialVersionUID = -3781766218680716955L;
    private Integer id;
    private String title;
    private BigDecimal price;
    private String description;
    private Integer sales;
    private String imgUrl;
   
}