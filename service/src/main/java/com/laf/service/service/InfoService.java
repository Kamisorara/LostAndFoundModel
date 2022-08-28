package com.laf.service.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laf.entity.entity.sys.Info;

public interface InfoService {

    //用户获取用户的联系方式发送info 给该用户提醒
    void addUserGetPhoneNumInfo(String message);

    //添加用户通知信息
    Boolean addInfo(Long userId, String message);

    //删除对用用户info
    Boolean deleteInfo(Long userId, Long infoId);

    //把info更为已读
    Boolean changeRead(Long infoId);

    //根据用户token id 分页获取该用户所有info
    IPage<Info> getUserInfoPage(Long userId, int pageNum, int pageSize);

    //根据info id 获取消息详情
    Info getInfoDetailByInfoId(Long infoId);
}
