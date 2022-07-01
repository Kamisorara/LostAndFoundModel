package com.laf.service.service.impl.laf;

import com.laf.dao.mapper.RankMapper;
import com.laf.entity.entity.sys.Rank;
import com.laf.entity.entity.tokenResp.UserResp;
import com.laf.service.service.LafIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LafIndexServiceImpl implements LafIndexService {

    @Autowired
    private RankMapper rankMapper;

    /**
     * 获取排名前三用户列表
     *
     * @return
     */
    @Override
    public List<UserResp> getTop3UserList() {
        List<UserResp> top3User = rankMapper.getTop3User();
        return top3User;
    }
}
