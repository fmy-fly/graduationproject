package com.suda.backend.service.impl.email;

import com.suda.backend.service.email.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendMailServiceImpl implements SendMailService {
    @Autowired
    private JavaMailSender javaMailSender;
    //发送人
    private String from = "1791477899@qq.com";
    //接收人

    //标题
    private String subject = "攻击警告";
    //正文
    private String context = "系统正处于攻击状态";
    @Override
    public void sendMail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from+"(IOT System)");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(context);
        javaMailSender.send(message);
    }
}
