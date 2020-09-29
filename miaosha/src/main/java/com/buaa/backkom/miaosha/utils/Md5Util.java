package com.buaa.backkom.miaosha.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: backkom
 * @Date: 2020/9/18 20:41
 */
public class Md5Util
{
    
    public static String Encode(String s) throws NoSuchAlgorithmException
    {
        MessageDigest md5 = MessageDigest.getInstance("md5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(md5.digest(s.getBytes()));
    }
}
