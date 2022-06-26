package com.laf.service.service.impl;


import com.laf.dao.mapper.UserMapper;
import com.laf.entity.entity.LoginUser;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.sys.User;
import com.laf.entity.utils.JwtUtil;
import com.laf.entity.utils.RedisCache;
import com.laf.service.service.LoginService;
import com.laf.service.service.VerifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    VerifyService verifyService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录
     */
    @Override
    public ResponseResult login(User user) {
        //AuthenticationManager的authenticate方法来进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);//如果认证通过这个结果就不为null
        //如果认证不通过给出对应提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        //如果用户的status是处于被封禁状态则登录失败
        Long userId = userMapper.selectUserIdByUserName(user.getUserName());
        String userStatus = userMapper.getUserStatus(userId);
        if (userStatus.equals("1")) {
            return new ResponseResult(400, "该账户已被管理员封禁，请联系管理员已获取详细信息！");
        } else {
            //如果认证通过使用userid生成jwt  存入ResponseBody进行返回
            LoginUser loginUser = (LoginUser) authenticate.getPrincipal(); //强转成LoginUser对象
            String userid = loginUser.getUser().getId().toString();
            //生成jwt
            String jwt = JwtUtil.createJWT(userid);
            Map<String, String> map = new HashMap<>();
            map.put("token", jwt);
            //把完整的用户信息存入redis
            redisCache.setCacheObject("login:" + userid, loginUser);
            return new ResponseResult(200, "登录成功", map);
        }
    }

    /**
     * 注册接口
     */
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
     * 退出
     */
    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        Long userid = loginUser.getUser().getId();
        redisCache.deleteObject("login:" + userid);
        return new ResponseResult(200, "退出成功");
    }

    /**
     * 修改密码
     */
    @Override
    public ResponseResult updatePassword(Long id, String oldPass, String newPass) {
        String userOldPass = userMapper.getUserOldPass(id);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean matches = encoder.matches(oldPass, userOldPass);
        if (matches) {
            BCryptPasswordEncoder newPassEncoder = new BCryptPasswordEncoder();
            String newPassEncode = newPassEncoder.encode(newPass);
            Integer update = userMapper.updateUserPassword(id, newPassEncode);
            if (update > 0) {
                return new ResponseResult(200, "更新密码成功！");
            } else {
                return new ResponseResult(400, "更新密码失败！");
            }
        } else {
            return new ResponseResult(400, "修改密码失败！请检查密码是否输入正确！");
        }
    }
}
