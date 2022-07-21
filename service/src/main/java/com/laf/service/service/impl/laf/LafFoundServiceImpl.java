package com.laf.service.service.impl.laf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laf.dao.mapper.laf.NoticeMapper;
import com.laf.entity.entity.laf.Notice;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.service.service.LafFoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LafFoundServiceImpl implements LafFoundService {
    @Autowired
    private NoticeMapper noticeMapper;

    /**
     * 分页分页获取所有拾物启事列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public IPage<NoticeSearchResp> getAllFoundNotice(int pageNum, int pageSize) {
        Page<NoticeSearchResp> page = new Page<>();
        //设置每页大小
        page.setSize(pageSize);
        //设置当前页码
        page.setCurrent(pageNum);
        return noticeMapper.getAllFoundNotice(page);
    }

    /**
     * 获取该启示详细信息
     *
     * @param noticeId
     * @return
     */
    @Override
    public NoticeSearchResp getNoticeDetailInfo(Long noticeId) {
        return noticeMapper.getNoticeDetail(noticeId);
    }

    /**
     * 获取该启示所有图片
     *
     * @param noticeId
     * @return
     */
    @Override
    public List<String> getNoticeAllPhotos(Long noticeId) {
        return noticeMapper.getAllNoticePhoto(noticeId);
    }

    /**
     * 创建拾物启示
     *
     * @param notice
     * @return
     */
    @Override
    public ResponseResult createFoundNotice(Notice notice, Long userId) {
        notice.setType("1").setView(0);
        notice.setCreateId(userId);
        int insert = noticeMapper.insert(notice);
        if (insert > 0) {
            return new ResponseResult(200, "创建拾物启事成功");
        } else {
            return new ResponseResult(400, "创建拾物启事失败，请重试");
        }
    }
}
