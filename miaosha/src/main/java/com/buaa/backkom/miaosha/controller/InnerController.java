package com.buaa.backkom.miaosha.controller;

import com.buaa.backkom.miaosha.dao.ItemStockDOMapper;
import com.buaa.backkom.miaosha.dao.OrderDOMapper;
import com.buaa.backkom.miaosha.dataobject.OrderDO;
import com.buaa.backkom.miaosha.response.CommonReturnType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: backkom
 * @Date: 2020/9/22 15:17
 */
@RestController
@CrossOrigin(origins = {"*"},allowCredentials = "true")
@Slf4j
public class InnerController
{
    @Autowired
    private OrderDOMapper orderDOMapper;
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Autowired
    private ItemStockDOMapper stockDOMapper;
    @GetMapping("/test/order")
    public CommonReturnType testOrder(Integer amount,Integer itemId) {
        
        stockDOMapper.decreaseStock(itemId,amount);
        return CommonReturnType.create(null);
    }
    
    @GetMapping("/redis")
    public void testRedis() {
        redisTemplate.opsForValue().set("redis","hello");
        String s =(String)redisTemplate.opsForValue().get("redis");
        log.info(s);
    }
    
}
