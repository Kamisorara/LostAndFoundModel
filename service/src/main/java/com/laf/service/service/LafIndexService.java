package com.laf.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laf.entity.entity.laf.lafResp.NoticeIndexResp;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.sys.Board;
import com.laf.entity.entity.tokenResp.UserResp;

import java.util.List;

public interface LafIndexService {

    //首页获取排名前三用户
    List<UserResp> getTop3UserList();

    //首页获取4条普通寻物启事
    List<NoticeIndexResp> getIndexSimpleLostList();

    //首页获取4条普通拾物启事
    List<NoticeIndexResp> getIndexSimpleFoundList();

    //首页获取4条紧急寻物启事
    List<NoticeIndexResp> getIndexSimpleUrgencyLostList();

    //首页获取公告信息
    List<Board> getIndexBoardList();

    //分页获取最近发布启示
    IPage<NoticeSearchResp> getRecentNotice(int pageNum, int pageSize);

    //根据启示关键字搜索，分页获取
    IPage<NoticeSearchResp> searchNoticeByKeyWords(String KeyWords, int pageNum, int pageSize);
}
