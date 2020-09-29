package com.buaa.backkom.miaosha.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeqDO {
    
    private Integer id;

    private Integer cur;

    private Integer step;
    
    private String name;

}