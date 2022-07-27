package com.laf.entity.entity.resp.messageResp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户留言信息回复类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class MessageResp {
    //留言id
    private Long id;
    //留言信息
    private String message;
    //用户id
    private Long userId;
    //用户昵称
    private String userName;
    //用户头像
    private String avatarUrl;
}
