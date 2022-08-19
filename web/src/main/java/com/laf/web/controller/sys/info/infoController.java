package com.laf.web.controller.sys.info;

import com.laf.service.service.InfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "信息通知相关接口", description = "需要sys:common:user权限")
public class infoController {
    @Autowired
    private InfoService infoService;


}
