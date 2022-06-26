package com.laf.web.controller.sys;

import com.laf.dao.mapper.MenuMapper;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.service.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户账号相关接口
 * 权限要求:sys:common:user
 */
@RestController
@RequestMapping("/sys/user-account")
@PreAuthorize("@ex.hasAuthority('sys:common:user')")
public class userAccount {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    LoginService loginService;


    /**
     * 退出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseResult logout() {
        return loginService.logout();
    }


    /**
     * 更新密码
     */
    @RequestMapping(value = "/update-password", method = RequestMethod.POST)
    public ResponseResult updatePassword(@RequestParam("id") Long id,
                                         @RequestParam("oldPassword") String oldPassword,
                                         @RequestParam("newPassword") String newPassword) {
        ResponseResult responseResult = loginService.updatePassword(id, oldPassword, newPassword);
        return responseResult;
    }
}
