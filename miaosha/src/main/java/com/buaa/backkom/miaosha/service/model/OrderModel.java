package com.buaa.backkom.miaosha.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: backkom
 * @Date: 2020/9/19 18:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 *  这只是一个单订单单商品的OrderModel
 */
public class OrderModel implements Serializable
{
    private static final long serialVersionUID = -488359567174665982L;
    /*
            订单流水号，一般为16位,前八位为年月日
            中间8位为自增序列
            最后两位为分库分表位
         */
    private String id;
    
    private Integer userId;
    
    private Integer itemId;
    
    //购买商品数量
    private Integer amount;
    
    //商品单价
    private BigDecimal price;
    
    //订单总价,有可能会有优惠什么的
    private BigDecimal orderPrice;
}
