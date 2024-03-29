package com.laf.web.controller.sys;

import com.laf.entity.constant.HttpStatus;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.tokenResp.UserDetailInfoResp;
import com.laf.entity.entity.tokenResp.UserResp;
import com.laf.service.service.UserInfoService;
import com.laf.service.service.utilService.tokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息相关接口
 */
@Api(tags = "用户信息相关接口", description = "需要sys:common:user权限")
@RestController
@PreAuthorize("@ex.hasAuthority('sys:common:user')")
@RequestMapping("/sys/user-info")
public class userInfo {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    private tokenService tokenService;

    /**
     * 根据token获取用户登录状态
     */
    @ApiOperation("根据token获取用户登录状态")
    @RequestMapping(value = "/get-status-login", method = RequestMethod.GET)
    public ResponseResult getUserInfo(HttpServletRequest request) throws Exception {
        Long userIdFromToken = tokenService.getUserIdFromToken(request);
        UserResp userInfo = userInfoService.getUserInfo(userIdFromToken);
        return new ResponseResult<>(HttpStatus.SUCCESS, "用户已经登录", userInfo);
    }

    /**
     * 根据他人id获取用户详情包括昵称头像
     */
    @ApiOperation("根据他人id获取用户详情包括昵称头像")
    @RequestMapping(value = "/get-user-info", method = RequestMethod.GET)
    public ResponseResult getOtherUserBasicInfo(@RequestParam("id") Long id) {
        UserResp userInfo = userInfoService.getUserInfo(id);
        return new ResponseResult<>(HttpStatus.SUCCESS, "获取" + userInfo.getUserName() + "用户信息成功", userInfo);
    }

    /**
     * 根据用户id 获取用户主页详情（包括用户，头像，昵称，帮助他人次数,用户个人主页背景图片等
     */
    @ApiOperation("根据用户id 获取用户主页详情（包括用户，头像，昵称，帮助他人次数,用户个人主页背景图片等")
    @RequestMapping(value = "/get-user-detail-info", method = RequestMethod.GET)
    public ResponseResult getUserDetailInfo(@RequestParam("id") Long id) {
        UserDetailInfoResp userDetailInfo = userInfoService.getUserDetailInfo(id);
        return new ResponseResult<>(HttpStatus.SUCCESS, "获取用户:" + userDetailInfo.getUserName() + "成功", userDetailInfo);
    }

    /**
     * 根据用户名搜索用户
     */
    @ApiOperation("根据用户名搜索用户")
    @RequestMapping(value = "/search-user", method = RequestMethod.POST)
    public ResponseResult getUserByUserName(@RequestParam("userName") String userName) {
        return userInfoService.getUserByUserName(userName);
    }

}
