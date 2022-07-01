package com.laf.service.service;

import com.laf.dao.mapper.BoardMapper;
import com.laf.entity.entity.laf.lafResp.NoticeIndexResp;
import com.laf.entity.entity.sys.Board;
import com.laf.entity.entity.sys.Rank;
import com.laf.entity.entity.tokenResp.UserResp;

import java.util.List;

public interface LafIndexService {

    //首页获取排名前三用户
    List<UserResp> getTop3UserList();

    //首页获取4条普通寻物启事
    List<NoticeIndexResp> getIndexSimpleLostList();

    //首页获取4条普通寻物启事
    List<NoticeIndexResp> getIndexSimpleFoundList();

    //首页获取4条紧急寻物启事
    List<NoticeIndexResp> getIndexSimpleUrgencyLostList();

    //首页获取公告信息
    List<Board> getIndexBoardList();
}
