package com.laf.service.service;

public interface InfoService {

    //添加用户通知信息
    Boolean addInfo(Long userId, String message);

    //删除对用用户info
    Boolean deleteInfo(Long userId, Long infoId);

    //把info更为已读
    Boolean changeRead(Long infoId);
}
