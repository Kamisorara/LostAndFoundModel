package com.laf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laf.entity.entity.sys.Avatar;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AvatarMapper extends BaseMapper<Avatar> {
    //把用户头像全都变成status 1（不启用）的状态
    int updateUserHeadStatus(Long id);

    //根据用户id获取用户头像
    String getUserAvatarById(Long id);
}
