package com.laf.dao.mapper.laf;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laf.entity.entity.laf.Notice;
import com.laf.entity.entity.laf.lafResp.NoticeIndexResp;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    //随机获取4条普通寻物启事信息(包括发布者id,发布者昵称,寻物启事id,相关图片)
    List<NoticeIndexResp> getSimpleLostInfo();

    //随机获取4条普通拾物启示信息
    List<NoticeIndexResp> getSimpleFoundList();

    //随机获取4条紧急寻物启事信息
    List<NoticeIndexResp> getSimpleUrgencyLostList();

    //根据启示关键字搜索对应启示并分类获取
    IPage<NoticeSearchResp> getNoticeByKeyWords(String keyWords, Page<NoticeSearchResp> page);

    //分页获取所有寻物启事列表
    IPage<NoticeSearchResp> getAllLostNotice(Page<NoticeSearchResp> page);

    //分页获取所有拾物启事列表
    IPage<NoticeSearchResp> getAllFoundNotice(Page<NoticeSearchResp> page);

    //根据启示id 获取启示详情(包括用户基本信息，启示基本信息和所有图片)
    NoticeSearchResp getNoticeDetail(Long noticeId);

    //获取该启示所有图片
    List<String> getAllNoticePhoto(Long noticeId);

    //获取该启示最多四张图片
    List<String> getAllNoticePhotoLimit4(Long noticeId);

    //根据用户id 获取该用户帮助的启示
    List<NoticeSearchResp> getUserHelpedNoticeByUserId(Long userId);

    //首页分页获取最近发布启示
    IPage<NoticeSearchResp> getRecentNotice(Page<NoticeSearchResp> page);

    //根据启示id获取创建者id
    Long getNoticeCreatedUserId(@Param("noticeId") Long noticeId);

    //更新启示状态为（已帮助）
    Integer updateNoticeDone(@Param("noticeId") Long noticeId, @Param("userId") Long userId);

    //根据用户id查询用户发布的启示个数（包括寻物和拾物）
    Integer countUserPostNotice(Long userId);

    //根据用户id查询用户帮助的启示的个数
    Integer countUserHelpedNotice(Long userId);

    //根据用户id 查询用户 待处理（未上传图片）启示个数
    Integer countUserHaveImgNotice(Long userId);

    //查询用户总启示个数
    Integer countUserTotalNoticeNum(Long userId);

    //查询对应启示是否有图片
    Integer countNoticeImg(Long noticeId);

    //获取用户发布启示列表
    List<Long> countUserPostNoticeList(Long userId);

    //逻辑删除用户notice
    Integer updateUserPersonalNoticeStatus(Long noticeId);


    //根据用户 id 分页获取该用户所发布的所有notice
    IPage<NoticeSearchResp> getUserReleaseNoticePageById(@Param("userId") Long userId, Page<NoticeSearchResp> page);

    //根据用户 id 分页获取该用户帮助的所有notice
    IPage<NoticeSearchResp> getUserHelpedNoticePageById(@Param("userId") Long userId, Page<NoticeSearchResp> page);
}
