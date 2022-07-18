package com.laf.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laf.entity.entity.laf.Notice;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.resp.ResponseResult;

import java.util.List;

public interface LafFoundService {
    //分页分页获取所有拾物启事列表
    IPage<NoticeSearchResp> getAllFoundNotice(int pageNum, int pageSize);

    //获取该启示详细信息
    NoticeSearchResp getNoticeDetailInfo(Long noticeId);

    //获取该启示所有图片
    List<String> getNoticeAllPhotos(Long noticeId);

    //创建拾物启示
    ResponseResult createFoundNotice(Notice notice, Long userId);
}
