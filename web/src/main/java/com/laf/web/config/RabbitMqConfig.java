package com.laf.web.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    //1.申明注册交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct_order_exchange", true, false);
    }

    //2.声明队列 sms.direct.queue
    @Bean
    public Queue smsQueue() {
        return new Queue("sms.direct.queue", true);
    }


    @Bean
    public Queue emailQueue() {
        return new Queue("email.direct.queue", true);
    }

    //3.完成绑定关系（队列和交换机完成绑定关系）
    @Bean
    public Binding smsBingding() {
        return BindingBuilder.bind((smsQueue())).to(directExchange()).with("sms");
    }

    @Bean
    public Binding emailBingding() {
        return BindingBuilder.bind((emailQueue())).to(directExchange()).with("email");
    }

}
