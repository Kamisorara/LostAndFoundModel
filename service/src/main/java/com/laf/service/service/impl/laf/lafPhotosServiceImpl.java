package com.laf.service.service.impl.laf;

import com.laf.dao.mapper.laf.lafPhotosMapper;
import com.laf.service.service.lafPhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class lafPhotosServiceImpl implements lafPhotosService {
    @Autowired
    private lafPhotosMapper lafPhotosMapper;

    /**
     * 判断是否拥有首页图
     *
     * @param noticeId
     * @return
     */
    @Override
    public Boolean judgeIndexDisplay(Long noticeId) {
        Integer num = lafPhotosMapper.countIndexDisplayPhoto(noticeId);
        return num != 0;
    }
}
