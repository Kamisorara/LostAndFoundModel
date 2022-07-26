package com.laf.service.service.utilService;

import javax.servlet.http.HttpServletRequest;

/**
 * token相关Service
 */
public interface tokenService {
    //从请求头token中获取用户id
    Long getUserIdFromToken(HttpServletRequest httpServletRequest) throws Exception;
}
