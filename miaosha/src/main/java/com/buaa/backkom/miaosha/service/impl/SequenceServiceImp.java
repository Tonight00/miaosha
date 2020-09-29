package com.buaa.backkom.miaosha.service.impl;

import com.buaa.backkom.miaosha.dao.SeqDOMapper;
import com.buaa.backkom.miaosha.dataobject.SeqDO;
import com.buaa.backkom.miaosha.error.BusinessException;
import com.buaa.backkom.miaosha.error.EmbusinessError;
import com.buaa.backkom.miaosha.service.interfaces.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: backkom
 * @Date: 2020/9/19 19:37
 */
@Service
public class SequenceServiceImp implements SequenceService
{
    
    @Autowired
    private SeqDOMapper seqDOMapper;
    
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String generateSequence (Integer userId) throws BusinessException
    {
            //前8位为时间信息
            LocalDateTime now = LocalDateTime.now();
            String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
            StringBuilder sb = new StringBuilder(nowDate);
            //中间6位为自增序列
            SeqDO seqDO = seqDOMapper.selectByName("order_info");
            if(seqDO == null) {
                throw  new BusinessException(EmbusinessError.PARAMETER_VALIDATION_ERROR);
            }
            Integer value = (seqDO.getCur() + seqDO.getStep() )% 100000000;
            seqDO.setCur(value);
            seqDOMapper.updateByPrimaryKey(seqDO);
            sb.append(value);
            //最后两位为分库分表位
            sb.append(userId%100);
            return sb.toString();
    }
}
