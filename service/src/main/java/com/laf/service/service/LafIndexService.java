package com.laf.service.service;

import com.laf.entity.entity.sys.Rank;
import com.laf.entity.entity.tokenResp.UserResp;

import java.util.List;

public interface LafIndexService {

    //首页获取排名前三用户
    List<UserResp> getTop3UserList();
}
