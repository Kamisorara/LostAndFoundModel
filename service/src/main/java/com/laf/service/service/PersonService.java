package com.laf.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.resp.userResp;

import java.util.List;

/**
 * 用户个人相关接口
 */
public interface PersonService {
    //根据该用户id 获取该用户帮助的启示
    List<NoticeSearchResp> getUserHelpedNotice(Long userId);

    //根据启示id 和用户id 查找创建者id如果创建者id 等于当前用户id 则说明当前启示是该用户创建
    Boolean JudgeCreatedUser(Long noticeId, Long userId);

    //根据启示id和帮助的用户用户id 设置启示为已完成状态
    Boolean updateNoticeDoneStatus(Long noticeId, Long userId);

    //根据用户id 获取用户名和头像url
    userResp getUserResp(Long userId);

    //根据用户id 查询用户发布的启示的个数和帮助他人的启示的个数
    List<Integer> countUserNotice(Long userId);

    //获取用户待处理列表
    List<NoticeSearchResp> getUserWaitingNoticeList(Long userId);

    //根据用户id 删除用户本人发布启示(逻辑删除)
    Boolean deleteUserPersonalNotice(Long userId, Long noticeId);

    //根据用户id 分页获取该用户的所有notice
    IPage<NoticeSearchResp> getUserNoticePageById(Long userId, int pageNum, int pageSize);

    //根据用户id 分页获取该用户所有已帮助的notice
    IPage<NoticeSearchResp> getUserHelpedNoticePageById(Long userId, int pageNum, int pageSize);

}
