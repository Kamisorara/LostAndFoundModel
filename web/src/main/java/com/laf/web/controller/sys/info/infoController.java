package com.laf.web.controller.sys.info;

import com.laf.entity.constant.HttpStatus;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.service.service.InfoService;
import com.laf.service.service.PersonService;
import com.laf.service.service.utilService.tokenService;
import com.laf.web.controller.laf.Person;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = "信息通知相关接口", description = "需要sys:common:user权限")
@RequestMapping("/laf/info")
@PreAuthorize("@ex.hasAuthority('sys:common:user')")
public class infoController {
    @Autowired
    private InfoService infoService;
    @Autowired
    private tokenService tokenService;

    @Autowired
    private PersonService personService;

    @RequestMapping(value = "/get-user-phone", method = RequestMethod.GET)
    public ResponseResult getUserPhoneNum(@RequestParam("id") Long userId,
                                          HttpServletRequest servletRequest) throws Exception {
        Long visitedUserId = tokenService.getUserIdFromToken(servletRequest);
        String userPhoneNum = personService.getUserPhoneNum(userId, visitedUserId);
        return new ResponseResult<>(HttpStatus.SUCCESS, userPhoneNum);
    }

}
