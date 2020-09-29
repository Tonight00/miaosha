package com.buaa.backkom.miaosha.service.impl;

import com.buaa.backkom.miaosha.service.interfaces.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * @Author: backkom
 * @Date: 2020/9/26 22:07
 */
@Service
@Slf4j
public class EmailServiceImp implements EmailService
{
    @Autowired
    private JavaMailSenderImpl mailSender;
    
    
    @Override
    public void sendMimeMail (String to, String subject, String content,String imgPath,String imgId) throws MessagingException, UnsupportedEncodingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        helper.setFrom(mailSender.getUsername(),"backkom");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
    
        FileSystemResource file = new FileSystemResource(imgPath);
        helper.addInline(imgId,file);
        mailSender.send(message);
        log.info("邮件发送成功");
    }
    
    public void sendOtpEmail(String to,String subject,String content) throws MessagingException, UnsupportedEncodingException
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true,"UTF-8");
        helper.setSubject(subject);
        helper.setText(content);
        helper.setFrom(mailSender.getUsername(),"backkom");
        helper.setTo(to);
        
        mailSender.send(message);
        log.info("验证码发送成功");
        
    }
    
    
    @Override
    public void sendAttachmentsMail (String to, String subject, String content, String filePath) {
        ;
    }
}
