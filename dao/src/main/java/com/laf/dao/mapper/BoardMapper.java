package com.laf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laf.entity.entity.sys.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper extends BaseMapper<Board> {

    //首页获取公告信息
    List<Board> getIndexBoardList();
}
