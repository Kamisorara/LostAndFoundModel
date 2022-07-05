package com.laf.service.service.impl;

import com.laf.dao.mapper.MessageMapper;
import com.laf.entity.entity.resp.messageResp.MessageResp;
import com.laf.service.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageMapper messageMapper;

    /**
     * 根据用户id 获取用户留言板信息
     *
     * @param id
     * @return
     */
    @Override
    public List<MessageResp> getUserLeaveMessage(Long id) {
        return messageMapper.getUserLeaveMessageById(id);
    }
}
