package com.laf.entity.entity.resp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 用户昵称头像回复类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class userResp {
    //用户id
    private Long id;
    //用户昵称
    private String userName;
    //用户用户头像
    private String avatarUrl;
}
