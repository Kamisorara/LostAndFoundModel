package com.laf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laf.entity.entity.sys.Photos;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface sysPhotosMapper extends BaseMapper<Photos> {
    //把用户status 全都变成 1（不启用）状态
    Integer updateUserPhotosStatus(Long userId);
}
