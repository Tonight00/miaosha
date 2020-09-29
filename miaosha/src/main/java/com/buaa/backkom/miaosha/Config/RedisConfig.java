package com.buaa.backkom.miaosha.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.stereotype.Component;

/**
 * @Author: backkom
 * @Date: 2020/9/26 16:36
 */
/*
JdkSerializationRedisSerializer：使用JDK的序列化手段(serializable接口，ObjectInputStrean，ObjectOutputStream)，数据以字节流存储，jdk序列化和反序列化数据
StringRedisSerializer：字符串编码，数据以string存储
JacksonJsonRedisSerializer：json格式存储
OxmSerializer：xml格式存储
 */
@Component
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
public class RedisConfig {
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        
        //首先解决key的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        
        //解决value的序列化方式
        /*Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
            ObjectMapper objectMapper =  new ObjectMapper();
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(DateTime.class,new JodaDateTimeJsonSerializer());
            simpleModule.addDeserializer(DateTime.class,new JodaDateTimeJsonDeserializer());
            objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
            objectMapper.registerModule(simpleModule);
            jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
            redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        */
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        redisTemplate.setValueSerializer(jdkSerializationRedisSerializer);
        redisTemplate.setHashValueSerializer(jdkSerializationRedisSerializer);
        return redisTemplate;
    }
}