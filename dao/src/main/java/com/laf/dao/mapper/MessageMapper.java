package com.laf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laf.entity.entity.resp.messageResp.MessageResp;
import com.laf.entity.entity.sys.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    //根据用户id 获取用户对应留言板(留言板信息包括对应用户昵称，头像，留言信息)
    List<MessageResp> getUserLeaveMessageById(Long userId);

    //根据用户token的id 和对应用户id 插入留言(暂不使用)
    int insertMessage(@Param("userId") Long userId, @Param("otherId") Long otherId, @Param("message") String message);
}
