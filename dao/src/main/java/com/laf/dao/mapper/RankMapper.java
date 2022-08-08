package com.laf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laf.entity.entity.sys.Rank;
import com.laf.entity.entity.tokenResp.UserResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RankMapper extends BaseMapper<Rank> {

    //获取帮助此时排行前三的用户
    List<UserResp> getTop3User();

    //获取用户帮助次数
    Integer getUserHelpedTimes(Long userId);

    //更新用户帮助次数
    Integer updateUserHelpTimes(@Param("helpTimes") Integer helpTimes, @Param("userId") Long userId);

}
