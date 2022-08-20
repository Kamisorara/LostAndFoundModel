package com.laf.service.service.impl;

import com.laf.entity.constant.RabbitMqConstant;
import com.laf.service.service.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送注册成功邮件信息
     *
     * @param mailAddr 邮件地址
     */
    @RabbitListener(queues = {RabbitMqConstant.EMAIL})
    @RabbitHandler
    @Override
    public void sendRegisterSuccessMail(String mailAddr) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("【LostAndFound】您的验证密码");
        message.setText("您的邮箱" + mailAddr + "\n已在http://laf.Kamisora.xyz 网站注册成功" + "\n如非本人操作请回复此文件");
        message.setTo(mailAddr);
        message.setFrom(from);
        mailSender.send(message);
    }
}
