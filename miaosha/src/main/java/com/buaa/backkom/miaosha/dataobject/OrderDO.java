package com.buaa.backkom.miaosha.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDO {
   
    private String id;

    private Integer userId;
    
    private Integer itemId;
    
    private BigDecimal price;
    
    private BigDecimal orderPrice;
    
    private Integer amount;
    
    public String getId() {
        return id;
    }

  
    public void setId(String id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    
    public Integer getItemId() {
        return itemId;
    }

   
    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

  
    public BigDecimal getPrice() {
        return price;
    }


    public void setPrice(BigDecimal price) {
        this.price = price;
    }

   
    public BigDecimal getOrderPrice() {
        return orderPrice;
    }

  
    public void setOrderPrice(BigDecimal orderPrice) {
        this.orderPrice = orderPrice;
    }

    public Integer getAmount() {
        return amount;
    }
    
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}