package com.laf.entity.constant;

/**
 * rabbitMq 交换机队列名称
 */
public class RabbitMqConstant {

    //交换机
    public static final String EXCHANGE = "direct_order_exchange";

    //队列名称
    public static final String SMS = "sms.direct.queue";

    public static final String EMAIL = "email.direct.queue";

    //routingKey

    public static final String SMS_ROUTING_KEY = "sns";

    public static final String EMAIL_ROUTING_KEY = "email";

}
