package com.buaa.backkom.miaosha.service.impl;

import com.buaa.backkom.miaosha.controller.viewobject.ItemVO;
import com.buaa.backkom.miaosha.dao.ItemDOMapper;
import com.buaa.backkom.miaosha.dao.ItemStockDOMapper;
import com.buaa.backkom.miaosha.dataobject.ItemDO;
import com.buaa.backkom.miaosha.dataobject.ItemStockDO;
import com.buaa.backkom.miaosha.error.BusinessException;
import com.buaa.backkom.miaosha.error.EmbusinessError;
import com.buaa.backkom.miaosha.service.interfaces.ItemService;
import com.buaa.backkom.miaosha.service.model.ItemModel;
import com.buaa.backkom.miaosha.validator.ValidatorImp;
import com.buaa.backkom.miaosha.validator.ValidatorResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: backkom
 * @Date: 2020/9/19 11:55
 */
@Component
public class ItemServiceImp implements ItemService
{
    @Autowired
    private ItemDOMapper itemDOMapper;
    
    @Autowired
    private ValidatorImp validatorImp;
    
    @Autowired
    private ItemStockDOMapper itemStockDOMapper;
    
    
    @Transactional
    public void register (ItemModel model) throws BusinessException
    {
      
       ValidatorResult result=validatorImp.validate(model);
       if(result.getHasErros()) {
           throw  new BusinessException(EmbusinessError.ITEM_NO_VALID);
       }
       
       ItemDO itemDO=convertFromModel(model);
       itemDOMapper.insertSelective(itemDO);
       ItemStockDO stockDO = convertFromDOAndModel(model,itemDO);
       itemStockDOMapper.insertSelective(stockDO);
    }
    
    
    @Override
    public List<ItemModel> getItems () {
     
     List<ItemDO> itemDos = itemDOMapper.selectAll();
     return itemDos.stream()
             .map(
                     item-> convertFromItemAndStock(
                             item,itemStockDOMapper.selectByItemId(item.getId())
                     )
             )
             .collect(Collectors.toList());
    }
    
   
    @Override
    public ItemModel getItem (Integer id)
    {
        
        ItemDO item = itemDOMapper.selectByPrimaryKey(id);
        ItemStockDO stock = itemStockDOMapper.selectByItemId(item.getId());
        return convertFromItemAndStock(item,stock);
    }
    
    private ItemModel convertFromItemAndStock (ItemDO itemDO,ItemStockDO stockDO) {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemDO,itemModel);
        itemModel.setStock(stockDO.getStock());
        return itemModel;
    }
    
    public  ItemDO convertFromModel(ItemModel model){
        ItemDO itemDo = new ItemDO();
        BeanUtils.copyProperties(model,itemDo);
        return itemDo;
    }
    
    public ItemStockDO convertFromDOAndModel(ItemModel model ,ItemDO itemDO) {
        
        ItemStockDO stockDO = new ItemStockDO();
        stockDO.setItemId(itemDO.getId());
        stockDO.setStock(model.getStock());
        return stockDO;
    }
}
