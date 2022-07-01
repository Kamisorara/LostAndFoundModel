package com.laf.dao.mapper.laf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laf.entity.entity.laf.Notice;
import com.laf.entity.entity.laf.lafResp.NoticeIndexResp;
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

}
