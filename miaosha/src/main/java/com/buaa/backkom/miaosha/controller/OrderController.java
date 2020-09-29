package com.buaa.backkom.miaosha.controller;

import com.buaa.backkom.miaosha.error.BusinessException;
import com.buaa.backkom.miaosha.error.EmbusinessError;
import com.buaa.backkom.miaosha.response.CommonReturnType;
import com.buaa.backkom.miaosha.service.interfaces.OrderService;
import com.buaa.backkom.miaosha.service.model.UserModel;
import com.buaa.backkom.miaosha.utils.CodeUtil;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.*;


/**
 * @Author: backkom
 * @Date: 2020/9/19 19:12
 */
@RestController
@CrossOrigin(origins = {"*"},allowCredentials = "true")
@Slf4j
public class OrderController
{
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    private ExecutorService executorService;
    
    private RateLimiter orderCreateRateLimiter;
    
    @PostConstruct
    public void init(){
        executorService = Executors.newFixedThreadPool(20);
        orderCreateRateLimiter = RateLimiter.create(500);
    }
    
    
    //生成验证码
    @PostMapping("/generateverifycode")
    public void generateverifycode(HttpServletRequest httpServletRequest, HttpServletResponse response) throws BusinessException, IOException
    {
        String token = httpServletRequest.getParameterMap().get("token")[0];
        if(StringUtils.isEmpty(token)){
            throw new BusinessException(EmbusinessError.USER_NOT_LOGIN,"用户还未登陆，不能生成验证码");
        }
        UserModel userModel = (UserModel) redisTemplate.opsForValue().get(token);
        if(userModel == null){
            throw new BusinessException(EmbusinessError.USER_NOT_LOGIN,"用户还未登陆，不能生成验证码");
        }
        
        Map<String,Object> map = CodeUtil.generateCodeAndPic();
        
        redisTemplate.opsForValue().set("verify_code_"+userModel.getId(),map.get("code"));
        redisTemplate.expire("verify_code_"+userModel.getId(),10, TimeUnit.MINUTES);
        
        ImageIO.write((RenderedImage) map.get("codePic"), "jpeg", response.getOutputStream());
    }
    
    @PostMapping("/order")
    public CommonReturnType addOrder(
                                    HttpServletRequest request,
                                    @RequestParam Integer itemId,
                                    @RequestParam Integer amount
                                     ) throws BusinessException {
        //令牌桶算法进行限流
        if(orderCreateRateLimiter.acquire() <= 0){
            throw new BusinessException(EmbusinessError.RATELIMIT);
        }
        
        Integer uid =Integer.parseInt(request.getParameterMap().get("uid")[0]);
        
        //同步调用线程池的submit方法
        //拥塞窗口为20的等待队列，用来队列化泄洪
        Future<Object> future = executorService.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                orderService.createOrder(uid,itemId,amount);
                return null;
            }
            //局部内部类，这里的uid，itemId，amount都不能被改变
        });
    
        try {
            future.get();
        } catch (InterruptedException e) {
            throw new BusinessException(EmbusinessError.UNKNOWN_ERROR);
        } catch (ExecutionException e) {
            throw new BusinessException(EmbusinessError.UNKNOWN_ERROR);
        }return CommonReturnType.create(null);
    }
    
  
}
