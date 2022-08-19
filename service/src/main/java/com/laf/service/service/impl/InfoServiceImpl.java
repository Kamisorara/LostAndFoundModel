package com.laf.service.service.impl;

import com.laf.dao.mapper.InfoMapper;
import com.laf.entity.entity.sys.Info;
import com.laf.service.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {


    @Autowired
    private InfoMapper infoMapper;

    /**
     * 添加通知
     */
    @Override
    public Boolean addInfo(Long userId, String message) {
        Info infoDetail = new Info();
        infoDetail.setMessageInfo(message);
        infoDetail.setUserId(userId);
        return infoMapper.insert(infoDetail) > 0;
    }

    /**
     * 删除通知
     */
    @Override
    public Boolean deleteInfo(Long userId, Long infoId) {
        Info infoDetail = infoMapper.getInfoDetailById(infoId);
        if (infoDetail.getUserId().equals(userId)) {
            return infoMapper.updateInfoStatus(infoId) > 0;
        } else {
            return false;
        }
    }

    /**
     * 更新已读
     */
    @Override
    public Boolean changeRead(Long infoId) {
        return infoMapper.updateInfoRead(infoId) > 0;
    }
}
