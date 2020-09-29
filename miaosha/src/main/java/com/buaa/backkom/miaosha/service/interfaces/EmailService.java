package com.buaa.backkom.miaosha.service.interfaces;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @Author: backkom
 * @Date: 2020/9/26 22:06
 */
public interface EmailService
{
    void sendMimeMail(String to, String subject, String content,String imgPath,String id) throws MessagingException, UnsupportedEncodingException;
    
    void sendAttachmentsMail(String to, String subject, String content, String filePath);
    
    void sendOtpEmail(String to,String subject,String content) throws MessagingException, UnsupportedEncodingException;
}
