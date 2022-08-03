package com.laf.entity.entity.tokenResp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * 用户编辑信息返回类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditInfoResp {

    //用户唯一ID
    private Long id;

    //用户名
    private String userName;

    //头像
    private String avatarUrl;

    //背景图片
    private String photoUrl;

    //联系方式
    private String phoneNumber;

    //电子邮箱
    private String email;

    //创建时间
    private Date createTime;

}
