package com.laf.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laf.entity.entity.laf.Notice;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.resp.ResponseResult;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public interface LafLostService {

    //分页分页获取所有寻物启事列表
    IPage<NoticeSearchResp> getAllLostNotice(int pageNum, int pageSize);

    //获取该启示详细信息
    NoticeSearchResp getNoticeDetailInfo(Long noticeId);

    //获取该启示所有图片
    List<String> getNoticeAllPhotos(Long noticeId);

    //创建寻物启事
    ResponseResult createLostNotice(Notice notice, Long userId);
}
