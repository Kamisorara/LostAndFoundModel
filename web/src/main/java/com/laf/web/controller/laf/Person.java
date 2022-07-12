package com.laf.web.controller.laf;

import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.resp.messageResp.MessageResp;
import com.laf.entity.entity.tokenResp.UserDetailInfoResp;
import com.laf.entity.utils.JwtUtil;
import com.laf.service.service.MessageService;
import com.laf.service.service.PersonService;
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
    private UserInfoService userInfoService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private PersonService personService;

    /**
     * 根据用户id 获取用户主页详情（包括用户，头像，昵称，帮助他人次数,用户个人主页背景图片等（根据Token的id）
     */
    @RequestMapping(value = "/get-person-detail", method = RequestMethod.GET)
    public ResponseResult getUserDetailInfo(HttpServletRequest request) throws Exception {
        //获取token的id
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String id = claims.get("sub").toString();
        Long id2 = Long.parseLong(id);
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

    /**
     * 根据用户id获取用户帮助过的启示列表
     */
    @RequestMapping(value = "/get-all-completed", method = RequestMethod.GET)
    public ResponseResult getUserHelpedNoticeList(@RequestParam("id") Long id) {
        List<NoticeSearchResp> userHelpedNotice =
                personService.getUserHelpedNotice(id);
        return new ResponseResult(200, "获取用户帮助列表成功", userHelpedNotice);
    }


    /**
     * 根据用户id 给用户留言
     */
    @RequestMapping(value = "/leave-message", method = RequestMethod.POST)
    public ResponseResult leaveMessageToOther(HttpServletRequest request,
                                              @RequestParam("toUserId") Long toUserId,
                                              @RequestParam("message") String message) throws Exception {
        //获取token的id
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String id = claims.get("sub").toString();
        Long id2 = Long.parseLong(id);
        Integer success = messageService.insertMessage(id2, toUserId, message);
        if (success > 0) {
            return new ResponseResult(200, "留言成功");
        } else {
            return new ResponseResult(400, "留言失败，请检查输入");
        }
    }


    /**
     * 填写用户id 将用户启示更新为已完成状态
     */
    @RequestMapping(value = "/helped-people", method = RequestMethod.POST)
    public ResponseResult helpedPeople(HttpServletRequest request,
                                       @RequestParam("id") Long id,
                                       @RequestParam("userId") Long userId) throws Exception {
        //获取token的id
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String personId = claims.get("sub").toString();
        Long id2 = Long.parseLong(personId);
        Boolean isPerson = personService.JudgeCreatedUser(id, id2);
        if (isPerson) {
            return new ResponseResult(200, "是本人");
        } else {
            return new ResponseResult(400, "不是本人");
        }
    }
}
