package com.laf.dao.mapper.laf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laf.entity.entity.laf.Notice;
import com.laf.entity.entity.laf.lafResp.NoticeIndexResp;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    //随机获取4条普通寻物启事信息(包括发布者id,发布者昵称,寻物启事id,相关图片)
    List<NoticeIndexResp> getSimpleLostInfo();

    //随机获取4条普通拾物启示信息
    List<NoticeIndexResp> getSimpleFoundList();

    //随机获取4条紧急寻物启事信息
    List<NoticeIndexResp> getSimpleUrgencyLostList();

    //根据启示关键字搜索对应启示并分类获取
    IPage<NoticeSearchResp> getNoticeByKeyWords(String keyWords, Page<NoticeSearchResp> page);

    //分页获取所有寻物启事列表
    IPage<NoticeSearchResp> getAllLostNotice(Page<NoticeSearchResp> page);

    //根据启示id 获取启示详情(包括用户基本信息，启示基本信息和所有图片)
    NoticeSearchResp getNoticeDetail(Long noticeId);

    //获取该启示所有图片
    List<String> getAllNoticePhoto(Long noticeId);


}