package com.laf.service.service.impl.laf;

import com.laf.dao.mapper.BoardMapper;
import com.laf.dao.mapper.RankMapper;
import com.laf.dao.mapper.laf.NoticeMapper;
import com.laf.entity.entity.laf.lafResp.NoticeIndexResp;
import com.laf.entity.entity.sys.Board;
import com.laf.entity.entity.tokenResp.UserResp;
import com.laf.service.service.LafIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LafIndexServiceImpl implements LafIndexService {

    @Autowired
    private RankMapper rankMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private BoardMapper boardMapper;

    /**
     * 获取排名前三用户列表
     *
     * @return
     */
    @Override
    public List<UserResp> getTop3UserList() {
        List<UserResp> top3User = rankMapper.getTop3User();
        return top3User;
    }

    /**
     * 首页获取四条普通寻物启事
     *
     * @return
     */
    @Override
    public List<NoticeIndexResp> getIndexSimpleLostList() {
        List<NoticeIndexResp> simpleLostInfo = noticeMapper.getSimpleLostInfo();
        return simpleLostInfo;
    }


    /**
     * 首页获取最多四条拾物启示
     *
     * @return
     */
    @Override
    public List<NoticeIndexResp> getIndexSimpleFoundList() {
        List<NoticeIndexResp> simpleFoundList = noticeMapper.getSimpleFoundList();
        return simpleFoundList;
    }

    /**
     * 首页获取四条紧急寻物启事
     *
     * @return
     */
    @Override
    public List<NoticeIndexResp> getIndexSimpleUrgencyLostList() {
        List<NoticeIndexResp> simpleUrgencyLostList = noticeMapper.getSimpleUrgencyLostList();
        return simpleUrgencyLostList;
    }

    /**
     * 首页获取公告信息
     *
     * @return
     */
    @Override
    public List<Board> getIndexBoardList() {
        List<Board> indexBoardList = boardMapper.getIndexBoardList();
        return indexBoardList;
    }


}
