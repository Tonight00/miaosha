package com.buaa.backkom.miaosha.service.interfaces;

import com.buaa.backkom.miaosha.error.BusinessException;

/**
 * @Author: backkom
 * @Date: 2020/9/19 18:45
 */
public interface OrderService
{
    public void createOrder(Integer userId, Integer itemId, Integer amount) throws BusinessException;
}
