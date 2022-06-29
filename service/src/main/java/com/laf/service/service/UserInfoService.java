package com.laf.service.service;

import com.laf.entity.entity.resp.ResponseResult;

public interface UserInfoService {
    ResponseResult updateUserInfo(Long userId, String userName, String phoneNumber);
}
