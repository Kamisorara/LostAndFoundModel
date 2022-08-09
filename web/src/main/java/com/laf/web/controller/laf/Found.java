package com.laf.web.controller.laf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laf.entity.constant.HttpStatus;
import com.laf.entity.entity.laf.Notice;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.service.service.LafFoundService;
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
 * 拾物启示相关接口
 */
@Api(tags = "Found拾物启示相关接口", description = "需要sys:common:user权限")
@RestController
@RequestMapping("/laf/found")
@PreAuthorize("@ex.hasAuthority('sys:common:user')")
public class Found {
    @Autowired
    private LafFoundService lafFoundService;

    @Autowired
    private tokenService tokenService;

    /**
     * 分页获取所有拾物启示列表
     */
    @ApiOperation("分页获取所有拾物启示列表")
    @RequestMapping(value = "/get-all-found", method = RequestMethod.GET)
    public ResponseResult getAllFoundList(@RequestParam("pageNum") int pageNum, @RequestParam("pageSize") int pageSize) {
        IPage<NoticeSearchResp> allFoundNotice = lafFoundService.getAllFoundNotice(pageNum, pageSize);
        return new ResponseResult<>(HttpStatus.SUCCESS, "获取分页拾物启事列表成功", allFoundNotice);
    }

    /**
     * 根据id 对应启示详情
     */
    @ApiOperation("根据id 对应启示详情")
    @RequestMapping(value = "/get-foundpost-detail", method = RequestMethod.GET)
    public ResponseResult getNoticeFoundNoticeDetail(@RequestParam("id") Long id) {
        NoticeSearchResp noticeDetailInfo = lafFoundService.getNoticeDetailInfo(id);
        List<String> noticeAllPhotos = lafFoundService.getNoticeAllPhotos(id);
        List<Object> result = new ArrayList<>();
        result.add(noticeDetailInfo);
        result.add(noticeAllPhotos);
        return new ResponseResult<>(HttpStatus.SUCCESS, "获取启示详情成功", result);
    }

    /**
     * 创建拾物启示
     */
    @ApiOperation("创建拾物启示")
    @RequestMapping(value = "/create-found-notice", method = RequestMethod.POST)
    public ResponseResult createLostNotice(Notice notice, HttpServletRequest request) throws Exception {
        Long userIdFromToken = tokenService.getUserIdFromToken(request);
        return lafFoundService.createFoundNotice(notice, userIdFromToken);
    }

}
