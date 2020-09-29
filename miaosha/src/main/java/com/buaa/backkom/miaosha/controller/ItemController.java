package com.buaa.backkom.miaosha.controller;

import com.buaa.backkom.miaosha.controller.viewobject.ItemVO;
import com.buaa.backkom.miaosha.error.BusinessException;
import com.buaa.backkom.miaosha.error.EmbusinessError;
import com.buaa.backkom.miaosha.response.CommonReturnType;
import com.buaa.backkom.miaosha.service.interfaces.ItemService;
import com.buaa.backkom.miaosha.service.model.ItemModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author: backkom
 * @Date: 2020/9/19 11:49
 */
@RestController
@Slf4j
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class ItemController
{
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    //商品创建
    @PostMapping("/item")
    public CommonReturnType addItem(ItemVO item) throws BusinessException
    {
       ItemModel model = convertFromVo(item);
       itemService.register(model);
       return CommonReturnType.create(null);
    }
    
    public ItemModel convertFromVo(ItemVO itemVO) throws BusinessException
    {
        if(itemVO == null) {
            throw new BusinessException(EmbusinessError.PARAMETER_VALIDATION_ERROR,"参数错误");
        }
        ItemModel model = new ItemModel();
        BeanUtils.copyProperties(itemVO,model);
        return model;
    }
    
    //商品列表
    @GetMapping("/items")
    public CommonReturnType getItems () {
        List<ItemModel> models = itemService.getItems();
        List<ItemVO> vos=models.stream().map(
            model -> {
                ItemVO vo = convertFromModel(model);
                vo.setPromoPrice(vo.getPrice().divide(BigDecimal.valueOf(2)));
                vo.setPromoStatus(2);
                return vo;
        }).collect(Collectors.toList());
        
        return CommonReturnType.create(vos);
    }
    
    //商品详情
    @GetMapping("/items/{id}")
    public CommonReturnType getItem(@PathVariable("id") Integer id) throws BusinessException
    {
        if(id==null || id==0) {
            throw new BusinessException(EmbusinessError.ITEM_NO_VALID);
        }
        
        //缓存判断
        ItemModel model = (ItemModel) redisTemplate.opsForValue().get("item_"+id);
        if(model == null) {
             model = itemService.getItem(id);
             redisTemplate.opsForValue().set("item_"+id,model);
             redisTemplate.expire("item_"+id,10, TimeUnit.MINUTES);
             log.info("商品"+model.getTitle()+"不在缓存中");
        }
        
        ItemVO vo = convertFromModel(model);
        vo.setPromoStatus(2);
        vo.setPromoPrice(vo.getPrice().divide(BigDecimal.valueOf(2)));
        return CommonReturnType.create(vo);
    }
    
    public ItemVO convertFromModel(ItemModel model){
        ItemVO vo = new ItemVO();
        BeanUtils.copyProperties(model,vo);
        return vo;
    }
    
}
