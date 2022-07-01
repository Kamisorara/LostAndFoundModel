package com.laf.web.controller.laf;


import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.tokenResp.UserResp;
import com.laf.service.service.LafIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/laf/index")
public class index {
    @Autowired
    private LafIndexService lafIndexService;

    @RequestMapping(value = "/get-top3-list", method = RequestMethod.GET)
    public ResponseResult getTop3UserList() {
        List<UserResp> top3UserList = lafIndexService.getTop3UserList();
        return new ResponseResult(200, "获取帮助rank成功", top3UserList);
    }

}
