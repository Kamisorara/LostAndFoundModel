package com.laf.service.service;


import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.sys.User;
import org.springframework.web.bind.annotation.RequestParam;


public interface LoginService {
    //登录
    ResponseResult login(User user);

    //注册
    ResponseResult register(String username,
                            String password,
                            String passwordRepeat,
                            String email,
                            String verify);

    //退出
    ResponseResult logout();

    //修改密码
    ResponseResult updatePassword(Long id, String oldPass, String newPass);
}
