package com.laf.web.controller.laf;

import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.resp.messageResp.MessageResp;
import com.laf.entity.entity.tokenResp.UserDetailInfoResp;
import com.laf.entity.utils.JwtUtil;
import com.laf.service.service.MessageService;
import com.laf.service.service.UserInfoService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户个人界面相关接口
 */
@RestController
@RequestMapping("/laf/person")
@PreAuthorize("@ex.hasAuthority('sys:common:user')")
public class Person {


    @Autowired
    UserInfoService userInfoService;

    @Autowired
    MessageService messageService;



    /**
     * 根据用户id 获取用户主页详情（包括用户，头像，昵称，帮助他人次数,用户个人主页背景图片等
     */
    @RequestMapping(value = "/get-person-detail", method = RequestMethod.GET)
    public ResponseResult getUserDetailInfo(HttpServletRequest request) throws Exception {
        //获取token
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String id = claims.get("sub").toString();
        long id2 = Long.parseLong(id);
        UserDetailInfoResp userDetailInfo = userInfoService.getUserDetailInfo(id2);
        return new ResponseResult(200, "获取用户:" + userDetailInfo.getUserName() + "成功", userDetailInfo);
    }

    /**
     * 根据用户id 获取用户对用留言板
     */
    @RequestMapping(value = "/get-person-board", method = RequestMethod.GET)
    public ResponseResult getPersonBoard(@RequestParam("id") Long id) {
        List<MessageResp> userLeaveMessage =
                messageService.getUserLeaveMessage(id);
        return new ResponseResult(200, "获取用户留言信息成功", userLeaveMessage);

    }

}
