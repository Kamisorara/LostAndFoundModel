package com.laf.web.controller.sys;

import com.laf.dao.mapper.UserMapper;
import com.laf.entity.entity.logincode.LoginProperties;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.resp.userResp;
import com.laf.entity.entity.sys.User;
import com.laf.entity.enums.LoginCodeEnum;
import com.laf.entity.utils.RedisCache;
import com.laf.service.service.LoginService;
import com.laf.service.service.UserInfoService;
import com.laf.service.service.VerifyService;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户账号相关(开放匿名接口)
 * 权限要求：null
 */
@Api(tags = "用户账号相关", description = "(开放匿名接口)")
@RestController
@RequestMapping("/sys/user-common")
public class userCommon {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VerifyService verifyService;

    @Resource
    private LoginProperties loginProperties;

    @Resource
    private RedisCache redisCache;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 登录接口
     */
    @ApiOperation("登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseResult login(HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String uuid = request.getParameter("uuid");
        String verifyCode = request.getParameter("verifyCode");
        String trueCode = redisCache.getCacheObject(uuid);
        if (!trueCode.equals(verifyCode)) {
            return new ResponseResult<>(400, "登录失败验证码错误！");
        } else {
            User user = new User();
            user.setUserName(username);
            user.setPassword(password);
            //登录操作
            return loginService.login(user);
        }
    }

    /**
     * 登录输入账号昵称时获取该用户的头像
     */
    @ApiOperation("登录输入账号昵称时获取该用户的头像")
    @RequestMapping(value = "/get-loginUser-avatar", method = RequestMethod.GET)
    public ResponseResult getLoginUserAvatarByUserName(HttpServletRequest request) {
        String username = request.getParameter("username");
        try {
            userResp userResp = userMapper.searchUserByUserName(username);
            return new ResponseResult<>(200, "获取该登录账号的头像成功！", userResp.getAvatarUrl());
        } catch (NullPointerException nullPointerException) {
            return new ResponseResult<>(400, "没有找到该用户的头像");
        }

    }

    /**
     * 注册接口
     */
    @ApiOperation("注册接口")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseResult register(@RequestParam("userName") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("password2") String passwordRepeat,
                                   @RequestParam("email") String email,
                                   @RequestParam("verify") String verify,
                                   HttpServletRequest request) {
        String uuid = request.getParameter("uuid");
        String verifyCode = request.getParameter("verifyCode");
        String trueCode = redisCache.getCacheObject(uuid);
        if (!trueCode.equals(verifyCode)) {
            return new ResponseResult<>(400, "登录失败验证码错误！");
        } else {
            return loginService.register(username, password, passwordRepeat, email, verify);
        }

    }

    /**
     * 发送验证码
     *
     * @param email 邮箱地址
     * @return verifyCode
     */
    @ApiOperation("发送验证码")
    @RequestMapping(value = "/verify-code", method = RequestMethod.POST)
    public ResponseResult verifyCode(@RequestParam("email") String email) {
        try {
            verifyService.sendVerifyCode(email);
            return new ResponseResult<>(200, "邮件发送成功");
        } catch (Exception e) {
            return new ResponseResult<>(400, "邮件发送失败！");
        }
    }

    /**
     * 验证码
     */
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @RequestMapping(value = "/loginVerifyCode", method = RequestMethod.GET)
    public Object getCode() {

        Captcha captcha = loginProperties.getCaptcha();
        String uuid = "code-key-" + UUID.randomUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.ARITHMETIC.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        redisCache.setCacheObject(uuid, captchaValue, Math.toIntExact(loginProperties.getLoginCode().getExpiration()), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return imgResult;
    }

    /**
     * 根据用户id 查询用户注册的email是否相撞
     */
    @ApiOperation(value = "根据用户id 查询用户注册的email是否相撞")
    @RequestMapping(value = "/judge-email-only", method = RequestMethod.POST)
    public ResponseResult judgeEmailOnlyOne(@RequestParam("email") String emailAddr) {
        Boolean only = userInfoService.judgeOnlyEmail(emailAddr);
        if (only) {
            return new ResponseResult<>(200, "是唯一邮箱");

        } else {
            return new ResponseResult<>(400, "不是唯一邮箱");
        }
    }


    /**
     * 根据用户名userName 判断用户名是否唯一
     */
    @ApiOperation(value = "根据用户id 判断用户名是否唯一")
    @RequestMapping(value = "/judge-userName-only", method = RequestMethod.POST)
    public ResponseResult judgeUserNameOnlyOne(@RequestParam("userName") String userName) {
        Boolean only = userInfoService.judgeOnlyUserName(userName);
        if (only) {
            return new ResponseResult(200, "是唯一用户名");
        } else {
            return new ResponseResult(400, "不是唯一用户名");
        }
    }

    /**
     * 测试用(之后删除)
     */
    @ApiOperation("测试接口（可忽略）")
    @RequestMapping(value = "/select-user", method = RequestMethod.GET)
    public ResponseResult select(@RequestParam("id") Long id) {
        User user = userMapper.selectById(id);
        return new ResponseResult(200, "获取", user);

    }
}
