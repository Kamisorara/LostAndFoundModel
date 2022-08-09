package com.laf.web.controller.sys;

import com.laf.dao.mapper.AvatarMapper;
import com.laf.dao.mapper.MenuMapper;
import com.laf.entity.constant.HttpStatus;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.sys.Avatar;
import com.laf.service.service.LoginService;
import com.laf.service.service.Oss.OssUploadService;
import com.laf.service.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户账号相关接口
 * 权限要求:sys:common:user
 */
@Api(tags = "userAccount用户账号相关接口", description = "需要sys:common:user权限")
@RestController
@RequestMapping("/sys/user-account")
@PreAuthorize("@ex.hasAuthority('sys:common:user')")
public class userAccount {
    @Autowired
    MenuMapper menuMapper;
    @Autowired
    LoginService loginService;
    @Autowired
    UserInfoService userInfoService;
    @Autowired
    OssUploadService ossUploadService;
    @Autowired
    AvatarMapper avatarMapper;

    /**
     * 退出
     */
    @ApiOperation("退出")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseResult logout() {
        return loginService.logout();
    }


    /**
     * 更新密码
     */
    @ApiOperation("更新密码")
    @RequestMapping(value = "/update-password", method = RequestMethod.POST)
    public ResponseResult updatePassword(@RequestParam("id") Long id,
                                         @RequestParam("oldPassword") String oldPassword,
                                         @RequestParam("newPassword") String newPassword) {
        return loginService.updatePassword(id, oldPassword, newPassword);
    }

    /**
     * 修改个人信息
     */
    @ApiOperation("修改个人信息")
    @RequestMapping(value = "/update-userInfo", method = RequestMethod.POST)
    public ResponseResult updateUserInfo(@RequestParam("id") Long userId,
                                         @RequestParam("userName") String userName,
                                         @RequestParam("phoneNumber") String phoneNumber) {
        return userInfoService.updateUserInfo(userId, userName, phoneNumber);
    }


    /**
     * 修改个人头像
     */
    @ApiOperation("修改个人头像")
    @RequestMapping(value = "/update-user-avatar", method = RequestMethod.POST)
    public ResponseResult updateUserAvatar(@RequestParam("file") MultipartFile multipartFile,
                                           HttpServletRequest servletRequest) {
        Avatar avatar = new Avatar();
        String avatarUrl = ossUploadService.uploadFile(multipartFile);
        String id = servletRequest.getHeader("userId");
        long userId = Long.parseLong(id);
        avatar.setUserId(userId);
        avatar.setStatus(0);
        avatar.setAvatarUrl(avatarUrl);
        avatarMapper.updateUserHeadStatus(userId);
        int succeed = avatarMapper.insert(avatar);
        return new ResponseResult<>(HttpStatus.SUCCESS, "头像修改成功", avatarUrl);
    }
}
