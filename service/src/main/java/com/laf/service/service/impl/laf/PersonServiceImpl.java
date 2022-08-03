package com.laf.service.service.impl.laf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laf.dao.mapper.UserMapper;
import com.laf.dao.mapper.laf.NoticeMapper;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.resp.userResp;
import com.laf.entity.entity.tokenResp.UserEditInfoResp;
import com.laf.service.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据用户id 获取用户帮助的启示列表
     *
     * @param userId 用户id
     * @return List<NoticeSearchResp>
     */
    @Override
    public List<NoticeSearchResp> getUserHelpedNotice(Long userId) {
        return noticeMapper.getUserHelpedNoticeByUserId(userId);
    }

    /**
     * 根据启示id 和用户id 查找创建者id如果创建者id 等于当前用户id 则说明当前启示是该用户创建
     *
     * @param noticeId 启示id
     * @param userId   用户id
     * @return true ot false
     */
    @Override
    public Boolean JudgeCreatedUser(Long noticeId, Long userId) {
        Long noticeCreatedUserId = noticeMapper.getNoticeCreatedUserId(noticeId);
        return noticeCreatedUserId.equals(userId);

    }

    /**
     * 根据启示id和帮助的用户用户id 设置启示为已完成状态
     *
     * @param noticeId 启示id
     * @param userId   用户id
     * @return true ot false
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
        List<Long> noticeIdLists = noticeMapper.countUserPostNoticeList(userId);

        List<NoticeSearchResp> waitingNum = noticeIdLists
                .stream()
                .filter(noticeId -> noticeMapper.countNoticeImg(noticeId) == 0)
                .map(noticeId -> noticeMapper.getNoticeDetail(noticeId))
                .filter(notice -> notice.getStatus().equals("0") && notice.getDone().equals("1"))
                .collect(Collectors.toList());

        result.add(userPostNoticeNum);
        result.add(waitingNum.size());
        result.add(userHelpedNoticeNum);
        return result;
    }

    /**
     * 获取用户待处理列表
     */
    @Override
    public List<NoticeSearchResp> getUserWaitingNoticeList(Long userId) {
        List<Long> noticeIdLists = noticeMapper.countUserPostNoticeList(userId);


        List<NoticeSearchResp> result = noticeIdLists
                .stream()
                .filter(noticeId -> noticeMapper
                        .countNoticeImg(noticeId) == 0)
                .map(noticeId -> noticeMapper.getNoticeDetail(noticeId))
                .filter(notice -> notice.getStatus().equals("0") && notice.getDone().equals("1"))
                .collect(Collectors.toList());

//        List<NoticeSearchResp> result = new ArrayList<>();
//        for (Long noticeId : noticeIdLists) {
//            Integer num = noticeMapper.countNoticeImg(noticeId);
//            if (num == 0) {
//                NoticeSearchResp noticeDetail = noticeMapper.getNoticeDetail(noticeId);
//                result.add(noticeDetail);
//            }
//        }
        return result;
    }

    /**
     * 根据用户id 删除用户本人发布启示(逻辑删除)
     *
     * @param userId   用户id
     * @param noticeId 启示id
     * @return true or false
     */
    @Override
    public Boolean deleteUserPersonalNotice(Long userId, Long noticeId) {
        Long noticeCreatedUserId = noticeMapper.getNoticeCreatedUserId(noticeId);
        if (noticeCreatedUserId.equals(userId)) {
            Integer num = noticeMapper.updateUserPersonalNoticeStatus(noticeId);
            return num > 0;
        }
        return false;
    }

    /**
     * 根据用户id 分页获取该用户的所有notice
     *
     * @param userId   用户id
     * @param pageNum  页数
     * @param pageSize 页大小
     * @return notice
     */
    @Override
    public IPage<NoticeSearchResp> getUserNoticePageById(Long userId, int pageNum, int pageSize) {
        Page<NoticeSearchResp> page = new Page<>();
        //设置每页大小
        page.setSize(pageSize);
        //设置当前页码
        page.setCurrent(pageNum);
        return noticeMapper.getUserReleaseNoticePageById(userId, page);
    }

    /**
     * 根据用户id 分页获取该用户所有已帮助的notice
     *
     * @param userId   用户id
     * @param pageNum  页数
     * @param pageSize 页大小
     * @return notice
     */
    @Override
    public IPage<NoticeSearchResp> getUserHelpedNoticePageById(Long userId, int pageNum, int pageSize) {
        Page<NoticeSearchResp> page = new Page<>();
        //设置每页大小
        page.setSize(pageSize);
        //设置当前页码
        page.setCurrent(pageNum);
        return noticeMapper.getUserHelpedNoticePageById(userId, page);
    }

    /**
     * 根据用户id 查询用户发布的启示的个数,帮助他人的启示的个数和浏览次数
     *
     * @param userId 用户id
     * @return numLists
     */
    @Override
    public List<Integer> getUserBasicInfoNum(Long userId) {
        List<Integer> result = new ArrayList<>();
        Integer userPostNoticeNum = noticeMapper.countUserPostNotice(userId);
        Integer userHelpedNoticeNum = noticeMapper.countUserHelpedNotice(userId);
        result.add(userPostNoticeNum);
        result.add(userHelpedNoticeNum);
        result.add(0);
        return result;
    }

    /**
     * 根据token id 获取该用户所有基本信息
     *
     * @param userTokenId 用户token id
     * @return userEditInfoResp
     */

    @Override
    public UserEditInfoResp getUserEditInfo(Long userTokenId) {
        return userMapper.getUserEditInfo(userTokenId);
    }
}
