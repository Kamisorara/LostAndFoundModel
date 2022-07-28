package com.laf.entity.entity.tokenResp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户详情回复类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

public class UserDetailInfoResp {
    private Long id;

    private String userName;

    private Integer helpTimes;

    private String avatarUrl;

    private String photoUrl;
}
