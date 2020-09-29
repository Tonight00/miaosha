package com.buaa.backkom.miaosha.controller;
import com.buaa.backkom.miaosha.controller.viewobject.UserVO;
import com.buaa.backkom.miaosha.error.BusinessException;
import com.buaa.backkom.miaosha.error.EmbusinessError;
import com.buaa.backkom.miaosha.response.CommonReturnType;
import com.buaa.backkom.miaosha.service.interfaces.EmailService;
import com.buaa.backkom.miaosha.service.interfaces.UserService;
import com.buaa.backkom.miaosha.service.model.UserModel;
import com.buaa.backkom.miaosha.utils.ConvertUtil;
import com.buaa.backkom.miaosha.utils.Md5Util;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Test;
import org.omg.CORBA.UserException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import sun.misc.BASE64Encoder;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Author: backkom
 * @Date: 2020/9/18 13:05
 */
@RestController
@Slf4j
@CrossOrigin(origins = {"*"},allowCredentials = "true")
public class UserController
{
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private RedisTemplate redisTemplate;
    
    @Autowired
    private EmailService  emailService;
    
    
    /**
     * 用户登录接口,注意如果登录成功，需要在浏览器端记录登录成功信息
     * @param telephone
     * @param password
     * @return
     * @throws BusinessException
     */
    @PostMapping("/login")
    public CommonReturnType login(HttpServletRequest request, String telephone,String password) throws BusinessException, NoSuchAlgorithmException
    {
        if(password == null || password.length() == 0 ) {
            throw new BusinessException(EmbusinessError.USER_LOGIN_FAIL);
        }
        if(telephone == null || telephone.length() == 0 ) {
            throw new BusinessException(EmbusinessError.USER_LOGIN_FAIL);
        }
        
        UserModel model=userService.validateLogin(telephone,Md5Util.Encode(password));
        
        if(redisTemplate.hasKey("user_id"+model.getId())){
            String token = (String) redisTemplate.opsForValue().get("user_id"+model.getId());
            log.info("注意: 用户"+model.getName()+"在重复登录");
            return CommonReturnType.create(token);
        }
        
        
        //生成token
    
        String  token = UUID.randomUUID().toString().replace("-","");
        redisTemplate.opsForValue().set("user_id"+model.getId(),token);
        redisTemplate.expire(token,1, TimeUnit.HOURS);
        
        redisTemplate.opsForValue().set(token,model.getId());
        redisTemplate.expire("user_id"+model.getId(),1,TimeUnit.HOURS);
       
        // request.getSession().setAttribute("IS_LOGIN",true);
        // request.getSession().setAttribute("LOGIN_USER",model);
        return CommonReturnType.create(token);
    }
    
    
    /**
     * 用户注册接口
     * @param request
     * @param telephone
     * @param otpCode
     * @param name
     * @param gender
     * @param password
     * @param age
     * @return
     * @throws BusinessException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping("/register")
    public CommonReturnType register(HttpServletRequest request,
                                     @RequestParam String telephone,
                                     @RequestParam String otpCode,
                                     @RequestParam String name,
                                     @RequestParam Byte gender,
                                     @RequestParam String password,
                                     @RequestParam Integer age) throws BusinessException, NoSuchAlgorithmException
    {
        String otp =(String)redisTemplate.opsForValue().get(telephone);
        if(otp==null || !otp.equals(otpCode)) {
            log.debug(otp);
             throw new BusinessException(EmbusinessError.NO_OPT_MESSAGE);
        }
        
        UserModel userModel = new UserModel();
        userModel.setTelephone(telephone);
        userModel.setName(name);
        userModel.setGender(gender);
        userModel.setAge(age);
        userModel.setEncryptPassword(Md5Util.Encode(password));
        userService.register(userModel);
        return CommonReturnType.create(null);
    }
    
    /**
     * 用户获取邮箱opt验证码接口
     * @param request
     * @param telephone
     * @return
             */
    @PostMapping("/otp")
    public CommonReturnType getOtp(HttpServletRequest request,
                                   @RequestParam String telephone) throws UnsupportedEncodingException, MessagingException
    {
        //按规则生成otp码
        Random random = new Random();
        Integer otp = random.nextInt(8999)+1000;
        redisTemplate.opsForValue().set(telephone,otp);
        log.info("发送成功，验证码为: "+otp);
        emailService.sendOtpEmail("1749407479@qq.com","otp验证码","验证码为"+otp);
        return CommonReturnType.create(null);
        
    }
    
    
    /**
     * 根据用户ID获取用户对象
     * @param id
     * @return
     * @throws Exception
     */
    @GetMapping("/user/{id}")
    public CommonReturnType getUser(@PathVariable("id") Integer id) throws Exception {
    
        UserModel userModel = userService.getUserById(id);
        if(userModel == null) {
            throw new BusinessException(EmbusinessError.USER_NOT_EXIST);
        }
        UserVO userVO = ConvertUtil.convertUserModel2VO(userModel);
        
        return CommonReturnType.create(userVO);
    }
    
}
