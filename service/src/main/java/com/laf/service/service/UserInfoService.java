package com.laf.service.service;

import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.tokenResp.UserDetailInfoResp;
import com.laf.entity.entity.tokenResp.UserResp;

public interface UserInfoService {
    //更新用户详细信息
    ResponseResult updateUserInfo(Long userId, String userName, String phoneNumber);

    //获取用户基本信息
    UserResp getUserInfo(Long userId);

    //获取用户头像信息
    String getUserAvatar(Long userId);

    //根据用户id 获取用户主页详情（包括用户，头像，昵称，帮助他人次数,用户个人主页背景图片等
    UserDetailInfoResp getUserDetailInfo(Long userId);

    //根据用户名userName 查找用户
    ResponseResult getUserByUserName(String userName);

    //根据用户id 查询用户注册的email是否相撞
    Boolean judgeOnlyEmail(String emailAddr);

    //根据用户userName 查询用户注册的userName是否相撞
    Boolean judgeOnlyUserName(String userName);

}
