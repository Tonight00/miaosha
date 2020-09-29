package com.buaa.backkom.miaosha;

import com.buaa.backkom.miaosha.dao.UserDOMapper;
import com.buaa.backkom.miaosha.dataobject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@MapperScan("com.buaa.backkom.miaosha.dao") // 这里扫描的是mapper接口
public class MiaoshaApplication
{
    @Autowired
    private UserDOMapper userDOMapper;
    public static void main (String[] args) {
        
        SpringApplication.run(MiaoshaApplication.class, args);
    }
    
}
