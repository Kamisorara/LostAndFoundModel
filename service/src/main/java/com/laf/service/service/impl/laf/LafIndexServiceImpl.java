package com.laf.service.service.impl.laf;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laf.dao.mapper.BoardMapper;
import com.laf.dao.mapper.RankMapper;
import com.laf.dao.mapper.laf.NoticeMapper;
import com.laf.entity.entity.laf.Notice;
import com.laf.entity.entity.laf.lafResp.NoticeIndexResp;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
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
        return rankMapper.getTop3User();
    }

    /**
     * 首页获取四条普通寻物启事
     *
     * @return
     */
    @Override
    public List<NoticeIndexResp> getIndexSimpleLostList() {
        return noticeMapper.getSimpleLostInfo();
    }


    /**
     * 首页获取最多四条拾物启示
     *
     * @return
     */
    @Override
    public List<NoticeIndexResp> getIndexSimpleFoundList() {
        return noticeMapper.getSimpleFoundList();
    }

    /**
     * 首页获取四条紧急寻物启事
     *
     * @return
     */
    @Override
    public List<NoticeIndexResp> getIndexSimpleUrgencyLostList() {
        return noticeMapper.getSimpleUrgencyLostList();
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

    /**
     * 根据启示关键字搜索，分页获取
     *
     * @param KeyWords
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public IPage<NoticeSearchResp> searchNoticeByKeyWords(String KeyWords, int pageNum, int pageSize) {
        Page<NoticeSearchResp> page = new Page<>();
        //设置每页大小
        page.setSize(pageSize);
        //设置当前页码
        page.setCurrent(pageNum);
        return noticeMapper.getNoticeByKeyWords(KeyWords, page);
    }


}
