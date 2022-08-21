package com.laf.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laf.entity.entity.sys.Info;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InfoMapper extends BaseMapper<Info> {
    //根据用户id 获取该用户的所有用户通知信息
    List<Info> getUserInfoByUserId(Long userId);


    //根据用户id 分页获取该用户通知信息
    IPage<Info> getUserInfoPageByUserId(@Param("userId") Long userId, Page<Info> pages);

    //更新用户Status状态
    Integer updateInfoStatus(Long infoId);

    //更新用户read状态
    Integer updateInfoRead(Long infoId);

    //根据info id 查询该Info详情
    Info getInfoDetailById(Long infoId);
}
