package com.laf.service.service.utilService.impl;


import com.laf.entity.utils.JwtUtil;
import com.laf.service.service.utilService.tokenService;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class tokenServiceImpl implements tokenService {

    /**
     * 从请求头token中获取用户id
     */
    @Override
    public Long getUserIdFromToken(HttpServletRequest httpServletRequest) throws Exception {
        //获取token的id
        String token = httpServletRequest.getHeader("token");
        Claims claims = JwtUtil.parseJWT(token);
        String personId = claims.get("sub").toString();
        return Long.parseLong(personId);
    }
}
