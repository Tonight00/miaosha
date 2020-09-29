package com.buaa.backkom.miaosha.dataobject;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ItemStockDO {

    private Integer id;
    private Integer stock;
    private Integer itemId;
}