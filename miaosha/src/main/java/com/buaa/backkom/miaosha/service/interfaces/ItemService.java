package com.buaa.backkom.miaosha.service.interfaces;

import com.buaa.backkom.miaosha.controller.viewobject.ItemVO;
import com.buaa.backkom.miaosha.error.BusinessException;
import com.buaa.backkom.miaosha.service.model.ItemModel;

import java.util.List;

/**
 * @Author: backkom
 * @Date: 2020/9/19 11:55
 */
public interface ItemService
{
    void register(ItemModel model) throws BusinessException;
    List<ItemModel> getItems();
    
    ItemModel getItem (Integer id);
}
