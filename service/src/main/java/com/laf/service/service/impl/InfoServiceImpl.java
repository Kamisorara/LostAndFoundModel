package com.laf.service.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.laf.dao.mapper.InfoMapper;
import com.laf.entity.constant.RabbitMqConstant;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.sys.Info;
import com.laf.service.service.InfoService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {


    @Autowired
    private InfoMapper infoMapper;

    /**
     * 用户获取用户的联系方式发送info 给该用户提醒
     */
    @RabbitListener(queues = RabbitMqConstant.SMS)
    @RabbitHandler
    @Override
    public void addUserGetPhoneNumInfo(String message) {
        try {
            Info infoDetail = new Info();
            String[] id = message.split("-");
            Long userId = Long.parseLong(id[0]);
            long visitedUserId = Long.parseLong(id[1]);
            infoDetail.setUserId(userId);
            String addMessageDetail = "id为" + visitedUserId + "的用户获取了你的联系方式";
            infoDetail.setMessageInfo(addMessageDetail);
            infoMapper.insert(infoDetail);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

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

    /**
     * 根据用户token id 分页获取该用户所有info
     *
     * @param userId 用户tokenId
     */
    @Override
    public IPage<Info> getUserInfoPage(Long userId, int pageNum, int pageSize) {
        Page<Info> page = new Page<>();
        //设置每页大小
        page.setSize(pageSize);
        //设置当前页码
        page.setCurrent(pageNum);
        return infoMapper.getUserInfoPageByUserId(userId, page);
    }
}
