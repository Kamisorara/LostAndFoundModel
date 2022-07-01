package com.laf.service.service.impl;

import com.laf.dao.mapper.AvatarMapper;
import com.laf.dao.mapper.UserMapper;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.tokenResp.UserDetailInfoResp;
import com.laf.entity.entity.tokenResp.UserResp;
import com.laf.service.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    AvatarMapper avatarMapper;

    //更新用户信息
    @Override
    public ResponseResult updateUserInfo(Long userId, String userName, String phoneNumber) {
        Integer ifSucceed = userMapper.updateUserInfoById(userId, userName, phoneNumber);
        if (ifSucceed > 0) {
            return new ResponseResult(200, "更新成功！");
        } else {
            return new ResponseResult(400, "更新失败，请重试!");
        }
    }


    //获取用户详情
    @Override
    public UserResp getUserInfo(Long userId) {
        UserResp userInfo = userMapper.getUserInfoByToken(userId);
        return userInfo;
    }

    //获取用户头像
    @Override
    public String getUserAvatar(Long userId) {
        String userAvatarUrl = avatarMapper.getUserAvatarById(userId);
        return userAvatarUrl;
    }

    //根据用户id 获取用户主页详情（包括用户，头像，昵称，帮助他人次数,用户个人主页背景图片等
    @Override
    public UserDetailInfoResp getUserDetailInfo(Long userId) {
        UserDetailInfoResp userDetailInfo = userMapper.getUserDetailInfo(userId);
        return userDetailInfo;
    }
}
