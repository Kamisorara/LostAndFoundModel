package com.laf.service.service;

import com.laf.entity.entity.resp.messageResp.MessageResp;

import java.util.List;

public interface MessageService {
    //根据用户id获取用户对应留言板信息
    List<MessageResp> getUserLeaveMessage(Long id);

    //根据用户token的id 和对应用户id 插入留言
    Integer insertMessage(Long userId, Long otherId, String message);
}
