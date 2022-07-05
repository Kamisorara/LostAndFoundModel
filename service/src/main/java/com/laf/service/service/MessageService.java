package com.laf.service.service;

import com.laf.entity.entity.resp.messageResp.MessageResp;

import java.util.List;

public interface MessageService {
    //根据用户id获取用户对应留言板信息
    List<MessageResp> getUserLeaveMessage(Long id);
}
