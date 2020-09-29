package com.buaa.backkom.miaosha.service.impl;

import com.buaa.backkom.miaosha.dao.ItemDOMapper;
import com.buaa.backkom.miaosha.dao.ItemStockDOMapper;
import com.buaa.backkom.miaosha.dao.OrderDOMapper;
import com.buaa.backkom.miaosha.dao.UserDOMapper;
import com.buaa.backkom.miaosha.dataobject.ItemDO;
import com.buaa.backkom.miaosha.dataobject.ItemStockDO;
import com.buaa.backkom.miaosha.dataobject.OrderDO;
import com.buaa.backkom.miaosha.error.BusinessException;
import com.buaa.backkom.miaosha.error.EmbusinessError;
import com.buaa.backkom.miaosha.service.interfaces.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author: backkom
 * @Date: 2020/9/19 18:48
 */
@Slf4j
@Service
public class OrderServiceImp implements OrderService
{
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private ItemDOMapper itemDOMapper;
    @Autowired
    private ItemStockDOMapper stockDOMapper;
    @Autowired
    private OrderDOMapper orderDOMapper;
    @Autowired
    private SequenceServiceImp sequenceServiceImp;
    @Autowired
    private RedisTemplate redisTemplate;
    
     /*
        login/items/item/order
        
        校验---（下单减库存---订单入库---） 放在redis里面、使用rocketMq进行回调
        //削峰:入口队列限制
        
        //反向代理、水平扩展、redis缓存、消息队列异步、削峰，protobuf序列化
        //
    
        
      5. rocketMQ异步回写
      6. rocketMQ消减流量风潮
      7. 令牌桶算法: 限流
   */
    
    
    @Override
    @Transactional
    public void createOrder (Integer userId, Integer itemId, Integer amount) throws BusinessException
    {
        //
        if(stockDOMapper.decreaseStock(itemId,amount)==0) {
            throw new BusinessException(EmbusinessError.PARAMETER_VALIDATION_ERROR,"库存不足");
        }
        
        //3. 订单入库
        ItemDO itemDO = itemDOMapper.selectByPrimaryKey(itemId);
        OrderDO orderDO = new OrderDO();
        orderDO.setItemId(itemId);
        orderDO.setUserId(userId);
        orderDO.setAmount(amount);
        orderDO.setPrice(itemDO.getPrice());
        orderDO.setOrderPrice(
                itemDO.getPrice()
                        .multiply(BigDecimal.valueOf(amount))
        );
        orderDO.setId(sequenceServiceImp.generateSequence(userId));
        
        
        //RocketMQ使用消息队列进行异步调用
        orderDOMapper.insertSelective(orderDO);
        itemDOMapper.addSales(orderDO.getAmount(),orderDO.getItemId());
    }
    
}
