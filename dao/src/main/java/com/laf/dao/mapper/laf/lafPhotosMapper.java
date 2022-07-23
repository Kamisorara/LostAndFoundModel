package com.laf.dao.mapper.laf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laf.entity.entity.laf.lafPhotos;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface lafPhotosMapper extends BaseMapper<lafPhotos> {
    //查询是否已经拥有首页图
    Integer countIndexDisplayPhoto(Long noticeId);
}
