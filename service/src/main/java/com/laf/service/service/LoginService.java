package com.laf.service.service;


import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.sys.User;


public interface LoginService {
    //登录
    ResponseResult login(User user);

    //退出
    ResponseResult logout();

    //修改密码
    ResponseResult updatePassword(Long id, String oldPass, String newPass);
}
