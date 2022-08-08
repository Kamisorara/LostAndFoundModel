package com.laf.service.service.impl.laf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laf.dao.mapper.BoardMapper;
import com.laf.dao.mapper.RankMapper;
import com.laf.dao.mapper.laf.NoticeMapper;
import com.laf.entity.entity.laf.lafResp.NoticeIndexResp;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.sys.Board;
import com.laf.entity.entity.tokenResp.UserResp;
import com.laf.service.service.LafIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
     * 分页获取最近发布启示
     *
     * @param pageNum
     * @param pageSize
     * @return
     */

    @Override
    public IPage<NoticeSearchResp> getRecentNotice(int pageNum, int pageSize) {
        Page<NoticeSearchResp> page = new Page<>();
        //设置每页大小
        page.setSize(pageSize);
        //设置当前页码
        page.setCurrent(pageNum);
        IPage<NoticeSearchResp> recentNotice = noticeMapper.getRecentNotice(page);
        List<NoticeSearchResp> records = recentNotice.getRecords();
        for (NoticeSearchResp notice : records) {
            List<String> allNoticePhotoLimit4 = noticeMapper.getAllNoticePhotoLimit4(notice.getId());
            notice.setLafPhotoUrls(allNoticePhotoLimit4);
        }
//以下代码有Bug 暂不启用
//        recentNotice.getRecords()
//                .stream()
//                .map(notice -> noticeMapper.getAllNoticePhotoLimit4(notice.getId()))
//                .collect(Collectors.toList());

        return recentNotice;

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
