package com.laf.web.controller.laf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laf.dao.mapper.laf.lafPhotosMapper;
import com.laf.entity.entity.laf.lafPhotos;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.resp.messageResp.MessageResp;
import com.laf.entity.entity.resp.userResp;
import com.laf.entity.entity.tokenResp.UserDetailInfoResp;
import com.laf.service.service.MessageService;
import com.laf.service.service.Oss.OssUploadService;
import com.laf.service.service.PersonService;
import com.laf.service.service.UserInfoService;
import com.laf.service.service.impl.fastdfs.fastDfsService;
import com.laf.service.service.lafPhotosService;
import com.laf.service.service.utilService.tokenService;
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
import java.io.IOException;
import java.util.List;

/**
 * 用户个人界面相关接口
 */
@Api(tags = "Person用户个人界面相关接口", description = "需要sys:common:user")
@RestController
@RequestMapping("/laf/person")
@PreAuthorize("@ex.hasAuthority('sys:common:user')")
public class Person {

    @Autowired
    private lafPhotosMapper lafPhotosMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private PersonService personService;

    @Autowired
    private OssUploadService ossUploadService;

    @Autowired
    private lafPhotosService lafPhotosService;

    @Autowired
    private tokenService tokenService;

    @Autowired
    private fastDfsService fastDfsService;

    /**
     * 根据用户id 获取用户主页详情（包括用户，头像，昵称，帮助他人次数,用户个人主页背景图片等（根据Token的id）
     */
    @ApiOperation("根据用户id 获取用户主页详情（包括用户，头像，昵称，帮助他人次数,用户个人主页背景图片等（根据Token的id）")
    @RequestMapping(value = "/get-person-detail", method = RequestMethod.GET)
    public ResponseResult getUserDetailInfo(HttpServletRequest request) throws Exception {
        Long userId = tokenService.getUserIdFromToken(request);
        UserDetailInfoResp userDetailInfo = userInfoService.getUserDetailInfo(userId);
        return new ResponseResult<>(200, "获取用户:" + userDetailInfo.getUserName() + "成功", userDetailInfo);
    }

    /**
     * 根据用户id 获取用户对用留言板
     */
    @ApiOperation("根据用户id 获取用户对用留言板")
    @RequestMapping(value = "/get-person-board", method = RequestMethod.GET)
    public ResponseResult getPersonBoard(@RequestParam("id") Long id) {
        List<MessageResp> userLeaveMessage = messageService.getUserLeaveMessage(id);
        return new ResponseResult<>(200, "获取用户留言信息成功", userLeaveMessage);
    }

    /**
     * 根据用户id获取用户帮助过的启示列表
     */
    @ApiOperation("根据用户id获取用户帮助过的启示列表")
    @RequestMapping(value = "/get-all-completed", method = RequestMethod.GET)
    public ResponseResult getUserHelpedNoticeList(@RequestParam("id") Long id) {
        List<NoticeSearchResp> userHelpedNotice = personService.getUserHelpedNotice(id);
        return new ResponseResult<>(200, "获取用户帮助列表成功", userHelpedNotice);
    }


    /**
     * 根据用户id 给用户留言
     */
    @ApiOperation("根据用户id 给用户留言")
    @RequestMapping(value = "/leave-message", method = RequestMethod.POST)
    public ResponseResult leaveMessageToOther(HttpServletRequest request, @RequestParam("toUserId") Long toUserId, @RequestParam("message") String message) throws Exception {
        Long userId = tokenService.getUserIdFromToken(request);
        Integer success = messageService.insertMessage(userId, toUserId, message);
        if (success > 0) {
            return new ResponseResult<>(200, "留言成功");
        } else {
            return new ResponseResult<>(400, "留言失败，请检查输入");
        }
    }


    /**
     * 填写用户id 将用户启示更新为已完成状态
     */
    @ApiOperation("填写用户id 将用户启示更新为已完成状态")
    @RequestMapping(value = "/helped-people", method = RequestMethod.POST)
    public ResponseResult helpedPeople(HttpServletRequest request, @RequestParam("id") Long id, @RequestParam("userId") Long userId) throws Exception {
        Long userIdFromToken = tokenService.getUserIdFromToken(request);
        Boolean isPerson = personService.JudgeCreatedUser(id, userIdFromToken);
        if (isPerson) {
            Boolean updateSucceed = personService.updateNoticeDoneStatus(id, userId);
            if (updateSucceed) {
                return new ResponseResult<>(200, "更改启示Done状态成功");
            } else {
                return new ResponseResult<>(400, "更新启示状态失败");
            }
        } else {
            return new ResponseResult<>(400, "不是本人启示");
        }
    }

    /**
     * 当用户输入失去焦点时自动提交并检索
     */
    @ApiOperation("当用户输入失去焦点时自动提交并检索")
    @RequestMapping(value = "/select-username-avatar", method = RequestMethod.POST)
    public ResponseResult selectUserNameAndAvatar(@RequestParam("id") Long id) {
        userResp userResp = personService.getUserResp(id);
        return new ResponseResult<>(200, "获取用户名和头像成功", userResp);
    }

    /**
     * 根据用户token中的用户id 获取徽标值
     */
    @ApiOperation("根据用户token中的用户id 获取徽标值")
    @RequestMapping(value = "/get-userNotice-badge", method = RequestMethod.GET)
    public ResponseResult getUserNoticeBadgeValue(HttpServletRequest request) throws Exception {
        Long userIdFromToken = tokenService.getUserIdFromToken(request);
        List<Integer> result = personService.countUserNotice(userIdFromToken);
        return new ResponseResult<>(200, "获取用户徽标值成功", result);
    }

    /**
     * 测试文件上传接口
     */
    //上传文件(开放权限)
    @ApiOperation("OSS文件上传（已弃用）")
    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public ResponseResult upLoadFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest servletRequest, @RequestParam("id") Long id) {

        String url = ossUploadService.uploadFile(multipartFile);
        lafPhotos lafPhotos = new lafPhotos();
        Boolean judge = lafPhotosService.judgeIndexDisplay(id);
        if (judge) {
            lafPhotos.setLafPhotoUrl(url);
            lafPhotos.setLafId(id);
        } else {
            lafPhotos.setLafPhotoUrl(url);
            lafPhotos.setLafId(id);
            lafPhotos.setIndexDisplay("0");
        }
        lafPhotosMapper.insert(lafPhotos);
        return new ResponseResult<>(200, "文件上传成功", url);
    }

    /**
     * fastDfs upload文件测试
     */
    @ApiOperation(" fastDfs upload文件测试(可以使用)")
    @RequestMapping(value = "/fastdfs-upload", method = RequestMethod.POST)
    public ResponseResult fastdfsUploadFile(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest servletRequest, @RequestParam("id") Long id) throws IOException {
        String resultUrl = fastDfsService.uploadImg(multipartFile);
        lafPhotos lafPhotos = new lafPhotos();
        Boolean judge = lafPhotosService.judgeIndexDisplay(id);
        if (judge) {
            lafPhotos.setLafPhotoUrl(resultUrl);
            lafPhotos.setLafId(id);
        } else {
            lafPhotos.setLafPhotoUrl(resultUrl);
            lafPhotos.setLafId(id);
            lafPhotos.setIndexDisplay("0");
        }
        lafPhotosMapper.insert(lafPhotos);
        return new ResponseResult<>(200, "文件上传成功", resultUrl);
    }

    /**
     * 获取用户待处理列表
     */
    @ApiOperation("获取用户待处理列表")
    @RequestMapping(value = "/user-waiting", method = RequestMethod.GET)
    public ResponseResult getUserWaitingNoticeLists(HttpServletRequest request) throws Exception {
        Long userIdFromToken = tokenService.getUserIdFromToken(request);
        List<NoticeSearchResp> userWaitingNoticeList = personService.getUserWaitingNoticeList(userIdFromToken);
        return new ResponseResult<>(200, "获取用户待处理列表成功", userWaitingNoticeList);
    }


    /**
     * 根据启示id删除自己发布的启示
     */
    @ApiOperation("根据启示id删除自己发布的启示")
    @RequestMapping(value = "/delete-personal-notice", method = RequestMethod.POST)
    public ResponseResult deleteUserPersonalNotice(HttpServletRequest request, @RequestParam("id") Long id) throws Exception {
        Long userId = tokenService.getUserIdFromToken(request);
        Boolean succeed = personService.deleteUserPersonalNotice(userId, id);
        if (succeed) {
            return new ResponseResult<>(200, "该notice删除成功");
        } else {
            return new ResponseResult<>(400, "notice删除失败");
        }
    }

    /**
     * 根据用户id 分页获取该用户帮助的所有启示
     */
    @ApiOperation("根据用户id 分页获取该用户帮助的所有启示")
    @RequestMapping(value = "/get-userReleased-page", method = RequestMethod.GET)
    public ResponseResult getUerReleasedNoticePage(@RequestParam("pageNum") int pageNum,
                                                   @RequestParam("pageSize") int pageSize,
                                                   @RequestParam("id") Long userId) {
        IPage<NoticeSearchResp> userNoticePageById =
                personService.getUserNoticePageById(userId, pageNum, pageSize);

        return new ResponseResult<>(200, "分页获取用户notice成功", userNoticePageById);

    }
}
