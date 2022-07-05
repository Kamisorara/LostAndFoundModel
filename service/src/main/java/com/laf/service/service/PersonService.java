package com.laf.service.service;

import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;

import java.util.List;

/**
 * 用户个人相关接口
 */
public interface PersonService {
    //根据该用户id 获取该用户帮助的启示
    List<NoticeSearchResp> getUserHelpedNotice(Long userId);

}
