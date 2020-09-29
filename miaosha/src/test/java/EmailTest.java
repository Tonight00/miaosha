import com.buaa.backkom.miaosha.MiaoshaApplication;
import com.buaa.backkom.miaosha.service.impl.EmailServiceImp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

/**
 * @Author: backkom
 * @Date: 2020/9/26 22:03
 */
@SpringBootTest(classes = MiaoshaApplication.class)
@RunWith(SpringRunner.class)
public class EmailTest
{
    @Autowired
    private EmailServiceImp emailServiceImp;
    
    @Test
    public void testEmail() throws UnsupportedEncodingException, MessagingException
    {
        String to="1749407479@qq.com";
        String subject="秒杀系统注册码";
        //String img="D:/timg.jpg";
        String content="秒杀系统验证码:22587";
        //emailServiceImp.sendMimeMail(to,subject,content,img,"1");
        emailServiceImp.sendOtpEmail(to,subject,content);
    }
    
    
}
