package com.laf.web.controller.sys;

import com.laf.dao.mapper.RankMapper;
import com.laf.dao.mapper.UserMapper;
import com.laf.dao.mapper.UserRoleMapper;
import com.laf.entity.entity.logincode.LoginProperties;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.sys.Rank;
import com.laf.entity.entity.sys.User;
import com.laf.entity.entity.sys.UserRole;
import com.laf.entity.enums.LoginCodeEnum;
import com.laf.entity.utils.RedisCache;
import com.laf.service.service.LoginService;
import com.laf.service.service.VerifyService;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    private UserRoleMapper userRoleMapper;

    @Autowired
    private VerifyService verifyService;

    @Autowired
    private RankMapper rankMapper;

    @Resource
    private LoginProperties loginProperties;

    @Resource
    private RedisCache redisCache;

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
            return new ResponseResult(400, "登录失败验证码错误！");
        } else {
            User user = new User();
            user.setUserName(username);
            user.setPassword(password);
            //登录操作
            return loginService.login(user);
        }
    }

    /**
     * 注册接口
     */
    @ApiOperation("注册接口")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseResult register(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("password2") String passwordRepeat,
                                   @RequestParam("email") String email,
                                   @RequestParam("verify") String verify) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (verifyService.doVerify(email, verify) && password.equals(passwordRepeat)) {
                User user = new User();
                user.setUserName(username);
                //这里需要加密存储 ,后面封装到service里面
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                String encode = encoder.encode(password);
                user.setPassword(encode);
                userMapper.insert(user);
                Long userIdInDataBase = userMapper.selectUserIdByUserName(username);
                UserRole userRole = new UserRole();
                userRole.setUserId(userIdInDataBase);
                userRole.setRoleId(1L);//默认注册用户默认附上普通用户角色
                userRoleMapper.insert(userRole);
                //在注册成功后在rank表中添加此用户
                Rank rank = new Rank();
                rank.setUserId(userIdInDataBase);
                rank.setHelpTimes(0);
                rankMapper.insert(rank);
                map.put("Info", email + "用户" + "注册成功！");
                return new ResponseResult(200, "注册成功!", map);
            } else {
                return new ResponseResult(400, "注册失败，请检查邮箱验证码或是密码是是否输入正确");
            }
        } catch (Exception e) {
            return new ResponseResult(400, "发生未知错误!");
        }
    }


    /**
     * 发送验证码
     *
     * @param email
     * @return
     */
    @ApiOperation("发送验证码")
    @RequestMapping(value = "/verify-code", method = RequestMethod.POST)
    public ResponseResult verifyCode(@RequestParam("email") String email) {
        try {
            verifyService.sendVerifyCode(email);
            return new ResponseResult(200, "邮件发送成功");
        } catch (Exception e) {
            return new ResponseResult(400, "邮件发送失败！");
        }
    }

    /**
     * 登录验证码
     *
     * @return
     */
    // fhadmin.cn
    @ApiOperation(value = "获取验证码", notes = "获取验证码")
    @GetMapping("/loginVerifyCode")
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
     * 测试用(之后删除)
     */
    @ApiOperation("测试接口（可忽略）")
    @RequestMapping(value = "/select-user", method = RequestMethod.GET)
    public ResponseResult select(@RequestParam("id") Long id) {
        User user = userMapper.selectById(id);
        return new ResponseResult(200, "获取", user);

    }
}
