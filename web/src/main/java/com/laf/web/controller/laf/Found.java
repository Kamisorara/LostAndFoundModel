package com.laf.web.controller.laf;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.laf.entity.entity.laf.Notice;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.utils.JwtUtil;
import com.laf.service.service.LafFoundService;
import io.jsonwebtoken.Claims;
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
@RestController
@RequestMapping("/laf/found")
@PreAuthorize("@ex.hasAuthority('sys:common:user')")
public class Found {
    @Autowired
    private LafFoundService lafFoundService;

    /**
     * 分页获取所有拾物启示列表
     */
    @RequestMapping(value = "/get-all-found", method = RequestMethod.GET)
    public ResponseResult getAllFoundList(@RequestParam("pageNum") int pageNum,
                                          @RequestParam("pageSize") int pageSize) {
        IPage<NoticeSearchResp> allFoundNotice =
                lafFoundService.getAllFoundNotice(pageNum, pageSize);
        return new ResponseResult(200, "获取分页拾物启事列表成功", allFoundNotice);
    }

    /**
     * 根据id 对应启示详情
     */
    @RequestMapping(value = "/get-foundpost-detail", method = RequestMethod.GET)
    public ResponseResult getNoticeFoundNoticeDetail(@RequestParam("id") Long id) {
        NoticeSearchResp noticeDetailInfo = lafFoundService.getNoticeDetailInfo(id);
        List<String> noticeAllPhotos =
                lafFoundService.getNoticeAllPhotos(id);
        List<Object> result = new ArrayList<>();
        result.add(noticeDetailInfo);
        result.add(noticeAllPhotos);
        return new ResponseResult(200, "获取启示详情成功", result);
    }

    /**
     * 创建拾物启示
     */
    @RequestMapping(value = "/create-found-notice", method = RequestMethod.POST)
    public ResponseResult createLostNotice(Notice notice, HttpServletRequest request) throws Exception {
        //获取token
        String token = request.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String id = claims.get("sub").toString();
        long id2 = Long.parseLong(id);
        return lafFoundService.createFoundNotice(notice, id2);
    }

}
