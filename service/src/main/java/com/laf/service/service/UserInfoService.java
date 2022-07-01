package com.laf.service.service;

import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.sys.Avatar;
import com.laf.entity.entity.tokenResp.UserResp;

public interface UserInfoService {
    //更新用户详细信息
    ResponseResult updateUserInfo(Long userId, String userName, String phoneNumber);

    //获取用户基本信息
    UserResp getUserInfo(Long userId);

    //获取用户头像信息
    String  getUserAvatar(Long userId);
}
