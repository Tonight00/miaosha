package com.buaa.backkom.miaosha.Config;

import com.buaa.backkom.miaosha.dao.ItemDOMapper;
import com.buaa.backkom.miaosha.dao.UserDOMapper;
import com.buaa.backkom.miaosha.error.BusinessException;
import com.buaa.backkom.miaosha.error.EmbusinessError;
import com.buaa.backkom.miaosha.service.model.UserModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: backkom
 * @Date: 2020/9/20 20:18
 */
@EnableWebMvc
@Configuration
@Slf4j
public class MyWebConfig implements WebMvcConfigurer {
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Autowired
    private UserDOMapper userDOMapper;
    
    @Autowired
    private ItemDOMapper itemDOMapper;
    
    
    
    @Override
    public void addInterceptors (InterceptorRegistry registry) {
    
        List<String> workPattern = new ArrayList<String>(){
            {
                add("/item/**");
                add("/order/**");
                add("/items/**");
            }
        };
        
        List<String> orderPattern = new ArrayList<String>(){
            {
                add("/order/**");
            }
        };
        
        HandlerInterceptor loginInter = new HandlerInterceptor() {
            @Override
            public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
            {
                String token = request.getParameterMap().get("token")[0];
                if(StringUtils.isEmpty(token)||!redisTemplate.hasKey(token)){
                    throw new BusinessException(EmbusinessError.USER_NOT_LOGIN);
                }
                return true;
            }
        };
        
        
        HandlerInterceptor orderInter = new HandlerInterceptor()
        {
            @Override
            public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
            {
                Map<String,String[]> mp = request.getParameterMap();
                if(mp==null) {
                    throw  new BusinessException(EmbusinessError.PARAMETER_VALIDATION_ERROR,"输入不能为空");
                }
                String token =  request.getParameterMap().get("token")[0];
                Integer userId = (Integer) redisTemplate.opsForValue().get(token);
                
                Integer itemId = Integer.parseInt(mp.get("itemId")[0]);
                Integer amount = Integer.parseInt(mp.get("amount")[0]);
                if(itemId == null || amount == null) {
                    throw new BusinessException(EmbusinessError.ITEM_NO_VALID);
                }
                
                //校验用户是否合法、商品是否存在、数量是否正确
                if(userId==null || userDOMapper.selectById(userId)==null) {
                    throw new BusinessException(EmbusinessError.PARAMETER_VALIDATION_ERROR,"用户不存在");
                }
                if(itemId == null || itemDOMapper.selectByPrimaryKey(itemId) == null ) {
                    throw new BusinessException(EmbusinessError.PARAMETER_VALIDATION_ERROR,"商品不存在");
                }
                if(amount == null || amount>99 || amount <0) {
                    throw new BusinessException(EmbusinessError.PARAMETER_VALIDATION_ERROR,"购买商品数量不合法");
                }
                
                
                
                
                
                return true;
            }
        };
        
        registry.addInterceptor(orderInter).addPathPatterns(orderPattern).order(10);
        registry.addInterceptor(loginInter).addPathPatterns(workPattern).order(1);
    }

}
