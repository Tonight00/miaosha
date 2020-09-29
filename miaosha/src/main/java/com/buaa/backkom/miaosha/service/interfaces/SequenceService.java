package com.buaa.backkom.miaosha.service.interfaces;

import com.buaa.backkom.miaosha.error.BusinessException;

/**
 * @Author: backkom
 * @Date: 2020/9/19 19:38
 */
public interface SequenceService
{
    public String generateSequence(Integer userId) throws BusinessException;
    
}
