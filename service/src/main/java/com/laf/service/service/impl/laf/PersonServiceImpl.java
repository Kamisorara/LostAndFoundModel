package com.laf.service.service.impl.laf;

import com.laf.dao.mapper.UserMapper;
import com.laf.dao.mapper.laf.NoticeMapper;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.resp.userResp;
import com.laf.service.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户id 获取用户帮助的启示列表
     *
     * @param userId
     * @return
     */
    @Override
    public List<NoticeSearchResp> getUserHelpedNotice(Long userId) {
        return noticeMapper.getUserHelpedNoticeByUserId(userId);
    }

    /**
     * 根据启示id 和用户id 查找创建者id如果创建者id 等于当前用户id 则说明当前启示是该用户创建
     *
     * @param noticeId
     * @param userId
     * @return
     */
    @Override
    public Boolean JudgeCreatedUser(Long noticeId, Long userId) {
        Long noticeCreatedUserId = noticeMapper.getNoticeCreatedUserId(noticeId);
        return noticeCreatedUserId.equals(userId);

    }

    /**
     * 根据启示id和帮助的用户用户id 设置启示为已完成状态
     *
     * @param noticeId
     * @param userId
     * @return
     */
    @Override
    public Boolean updateNoticeDoneStatus(Long noticeId, Long userId) {
        Integer success = noticeMapper.updateNoticeDone(noticeId, userId);
        return success > 0;
    }

    /**
     * 根据用户id 获取用户名和头像url
     *
     * @param userId
     * @return
     */
    @Override
    public userResp getUserResp(Long userId) {
        return userMapper.getUserNameAndAvatarByUserId(userId);
    }

    /**
     * 根据用户id 查询用户发布的启示的个数和帮助他人的启示的个数
     *
     * @param userId
     * @return
     */
    @Override
    public List<Integer> countUserNotice(Long userId) {
        List<Integer> result = new ArrayList<>();
        Integer userPostNoticeNum = noticeMapper.countUserPostNotice(userId);
        Integer userHelpedNoticeNum = noticeMapper.countUserHelpedNotice(userId);
        result.add(userPostNoticeNum);
        result.add(0);
        result.add(userHelpedNoticeNum);
        return result;
    }
}
