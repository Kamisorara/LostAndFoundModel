package com.laf.web.controller.sys;

import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.tokenResp.UserDetailInfoResp;
import com.laf.entity.entity.tokenResp.UserResp;
import com.laf.entity.utils.JwtUtil;
import com.laf.service.service.UserInfoService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@PreAuthorize("@ex.hasAuthority('sys:common:user')")
@RequestMapping("/sys/user-info")
public class userInfo {

    @Autowired
    UserInfoService userInfoService;

    /**
     * 根据token获取用户登录状态
     */
    @RequestMapping(value = "/get-status-login", method = RequestMethod.GET)
    public ResponseResult getUserInfo(HttpServletRequest request) throws Exception {
        //获取token
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String id = claims.get("sub").toString();
        long id2 = Long.parseLong(id);
        UserResp userInfo = userInfoService.getUserInfo(id2);
        return new ResponseResult(200, "用户已经登录", userInfo);

    }

    /**
     * 根据他人id获取用户详情包括昵称头像
     */
    @RequestMapping(value = "/get-user-info", method = RequestMethod.GET)
    public ResponseResult getOtherUserBasicInfo(@RequestParam("id") Long id) {
        UserResp userInfo = userInfoService.getUserInfo(id);
        return new ResponseResult(200, "获取" + userInfo.getUserName() + "用户信息成功", userInfo);
    }

    /**
     * 根据用户id 获取用户主页详情（包括用户，头像，昵称，帮助他人次数,用户个人主页背景图片等
     */
    @RequestMapping(value = "/get-user-detail-info", method = RequestMethod.GET)
    public ResponseResult getUserDetailInfo(@RequestParam("id") Long id) {
        UserDetailInfoResp userDetailInfo = userInfoService.getUserDetailInfo(id);
        return new ResponseResult(200, "获取用户:" + userDetailInfo.getUserName() + "成功", userDetailInfo);
    }

}
