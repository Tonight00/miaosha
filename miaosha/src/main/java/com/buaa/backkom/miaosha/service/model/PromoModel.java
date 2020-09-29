package com.buaa.backkom.miaosha.service.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: backkom
 * @Date: 2020/9/19 20:19
 */
@Data
public class PromoModel implements Serializable
{
    private static final long serialVersionUID = -2087440853596123580L;
    private Integer id;
    private String promoName;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    private Integer itemId;
    
    private BigDecimal promoItemPrice;
}
