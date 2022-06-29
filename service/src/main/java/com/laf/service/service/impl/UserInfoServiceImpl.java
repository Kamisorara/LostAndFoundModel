package com.laf.service.service.impl;

import com.laf.dao.mapper.UserMapper;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseResult updateUserInfo(Long userId, String userName, String phoneNumber) {
        Integer ifSucceed = userMapper.updateUserInfoById(userId, userName, phoneNumber);
        if (ifSucceed > 0) {
            return new ResponseResult(200, "更新成功！");
        } else {
            return new ResponseResult(400, "更新失败，请重试!");
        }
    }
}
