package com.laf.web.controller.laf;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laf.entity.entity.laf.lafResp.NoticeIndexResp;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.sys.Board;
import com.laf.entity.entity.tokenResp.UserResp;
import com.laf.service.service.LafIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 主页相关接口
 */
@RestController
@RequestMapping("/laf/index")
public class index {
    @Autowired
    private LafIndexService lafIndexService;


    /**
     * 首页获取公告
     */
    @RequestMapping(value = "/get-board-info", method = RequestMethod.GET)
    public ResponseResult getBoardList() {
        List<Board> indexBoardList = lafIndexService.getIndexBoardList();
        return new ResponseResult(200, "获取首页公告成功!", indexBoardList);
    }

    /**
     * 获取帮助次数最多的前三名用户
     */
    @RequestMapping(value = "/get-top3-list", method = RequestMethod.GET)
    public ResponseResult getTop3UserList() {
        List<UserResp> top3UserList = lafIndexService.getTop3UserList();
        return new ResponseResult(200, "获取帮助rank成功", top3UserList);
    }

    /**
     * 获取最多四条普通寻物启事信息
     */
    @RequestMapping(value = "/get-lost-simple-info", method = RequestMethod.GET)
    public ResponseResult getSimpleLostInfo() {
        List<NoticeIndexResp> indexSimpleLostList = lafIndexService.getIndexSimpleLostList();
        return new ResponseResult(200, "获取首页普通寻物启事成功", indexSimpleLostList);
    }

    /**
     * 获取最多四条普通拾物启事信息
     */
    @RequestMapping(value = "/get-found-simple-info", method = RequestMethod.GET)
    public ResponseResult getSimpleFoundList() {
        List<NoticeIndexResp> indexSimpleFoundList = lafIndexService.getIndexSimpleFoundList();
        return new ResponseResult(200, "获取首页拾物启示成功", indexSimpleFoundList);
    }

    /**
     * 首页获取四条紧急寻物启事
     */
    @RequestMapping(value = "/get-urgency-lost-info", method = RequestMethod.GET)
    public ResponseResult getSimpleUrgencyLostList() {
        List<NoticeIndexResp> indexSimpleUrgencyLostList = lafIndexService.getIndexSimpleUrgencyLostList();
        return new ResponseResult(200, "首页获取紧急寻物启事成功", indexSimpleUrgencyLostList);
    }

    /**
     * 分页获取根据KeyWords搜索到的内容
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public ResponseResult getSearchResp(@RequestParam("keyWords") String keyWords,
                                        @RequestParam("pageNum") int pageNum,
                                        @RequestParam("pageSize") int pageSize) {

        IPage<NoticeSearchResp> noticeSearchRespIPage =
                lafIndexService.searchNoticeByKeyWords(keyWords, pageNum, pageSize);

        return new ResponseResult(200, "搜索分页获取成功", noticeSearchRespIPage);

    }


}
