package com.laf.service.service.impl.laf;

import com.laf.dao.mapper.laf.NoticeMapper;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.service.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    NoticeMapper noticeMapper;

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
}
