package com.laf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laf.entity.entity.sys.Rank;
import com.laf.entity.entity.tokenResp.UserResp;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RankMapper extends BaseMapper<Rank> {

    //获取帮助此时排行前三的用户
    List<UserResp> getTop3User();
}
