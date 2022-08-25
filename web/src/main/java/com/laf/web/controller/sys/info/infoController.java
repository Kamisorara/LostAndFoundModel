package com.laf.web.controller.sys.info;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laf.entity.constant.HttpStatus;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.sys.Info;
import com.laf.service.service.InfoService;
import com.laf.service.service.PersonService;
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

@RestController
@Api(tags = "信息通知相关接口", description = "需要sys:common:user权限")
@RequestMapping("/sys/info")
@PreAuthorize("@ex.hasAuthority('sys:common:user')")
public class infoController {
    @Autowired
    private InfoService infoService;

    @Autowired
    private tokenService tokenService;

    @Autowired
    private PersonService personService;


    /**
     * 获取用户联系方式，并通过rabbitmq发送通知给对应用户
     */
    @ApiOperation("获取用户联系方式")
    @RequestMapping(value = "/get-user-phone", method = RequestMethod.GET)
    public ResponseResult getUserPhoneNum(@RequestParam("id") Long userId,
                                          HttpServletRequest servletRequest) throws Exception {
        Long visitedUserId = tokenService.getUserIdFromToken(servletRequest);
        String userPhoneNum = personService.getUserPhoneNum(userId, visitedUserId);
        return new ResponseResult<>(HttpStatus.SUCCESS, userPhoneNum);
    }


    /**
     * 根据用户token id 分页获取该用户所有通知
     */
    @ApiOperation("根据用户token id 分页获取该用户所有通知")
    @RequestMapping(value = "/get-user-info-page", method = RequestMethod.GET)
    public ResponseResult getUserInfoPage(HttpServletRequest servletRequest, @RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) throws Exception {
        Long userIdFromToken = tokenService.getUserIdFromToken(servletRequest);
        IPage<Info> userInfoPage =
                infoService.getUserInfoPage(userIdFromToken, pageNum, pageSize);
        return new ResponseResult<>(HttpStatus.SUCCESS, "分页获取该用户info成功", userInfoPage);
    }


}
