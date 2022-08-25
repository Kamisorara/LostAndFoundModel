package com.laf.web.controller.laf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laf.entity.constant.HttpStatus;
import com.laf.entity.entity.laf.Notice;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.service.service.LafLostService;
import com.laf.service.service.utilService.tokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 寻物启事相关接口
 */
@Api(tags = "Lost寻物启事相关接口", description = "需要sys:common:user权限")
@RestController
@RequestMapping("/laf/lost")
@PreAuthorize("@ex.hasAuthority('sys:common:user')")
public class Lost {

    @Autowired
    private LafLostService lafLostService;

    @Autowired
    private tokenService tokenService;

    /**
     * 分页获取所有寻物启事
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation("分页获取所有寻物启事")
    @RequestMapping(value = "/get-all-lost", method = RequestMethod.GET)
    public ResponseResult getAllLostNotice(@RequestParam("pageNum") int pageNum,
                                           @RequestParam("pageSize") int pageSize) {
        IPage<NoticeSearchResp> allLostNotice =
                lafLostService.getAllLostNotice(pageNum, pageSize);

        return new ResponseResult<>(HttpStatus.SUCCESS, "获取分页寻物启事列表成功", allLostNotice);
    }


    /**
     * 根据启示id获取启示详情
     */
    @ApiOperation("根据启示id获取启示详情")
    @RequestMapping(value = "/get-foundpost-detail", method = RequestMethod.GET)
    public ResponseResult getLostNoticeDetail(@RequestParam("id") Long id) {
        NoticeSearchResp noticeDetailInfo = lafLostService.getNoticeDetailInfo(id);
        List<String> noticeAllPhotos = lafLostService.getNoticeAllPhotos(id);
        List<Object> result = new ArrayList<>();
        result.add(noticeDetailInfo);
        result.add(noticeAllPhotos);
        return new ResponseResult<>(HttpStatus.SUCCESS, "获取启示详情成功", result);
    }

    /**
     * 创建寻物启事
     */
    @ApiOperation("创建寻物启事")
    @RequestMapping(value = "/create-lost-notice", method = RequestMethod.POST)
    public ResponseResult createLostNotice(Notice notice, HttpServletRequest request) throws Exception {
        Long userIdFromToken = tokenService.getUserIdFromToken(request);
        return lafLostService.createLostNotice(notice, userIdFromToken);
    }
}
