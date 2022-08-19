package com.laf.service.service;

/**
 * 邮件相关api
 */
public interface EmailService {

    //发送注册成功邮件信息
    void sendRegisterSuccessMail(String mailAddr);

}
