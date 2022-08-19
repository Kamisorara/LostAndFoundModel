package com.laf.service.service.impl;


import com.laf.dao.mapper.RankMapper;
import com.laf.dao.mapper.UserMapper;
import com.laf.dao.mapper.UserRoleMapper;
import com.laf.entity.constant.HttpStatus;
import com.laf.entity.constant.RabbitMqConstant;
import com.laf.entity.entity.LoginUser;
import com.laf.entity.entity.resp.ResponseResult;
import com.laf.entity.entity.sys.Rank;
import com.laf.entity.entity.sys.User;
import com.laf.entity.entity.sys.UserRole;
import com.laf.entity.utils.JwtUtil;
import com.laf.entity.utils.RedisCache;
import com.laf.service.service.LoginService;
import com.laf.service.service.UserInfoService;
import com.laf.service.service.VerifyService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RankMapper rankMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
            return new ResponseResult(HttpStatus.BAD_REQUEST, "该账户已被管理员封禁，请联系管理员已获取详细信息！");
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
            return new ResponseResult(HttpStatus.SUCCESS, "登录成功", map);
        }
    }

    /**
     * 注册
     *
     * @param username       用户名
     * @param password       密码
     * @param passwordRepeat 重复密码
     * @param email          邮箱地址
     * @param verify         邮箱验证码
     */
    @Override
    public ResponseResult register(String username,
                                   String password,
                                   String passwordRepeat,
                                   String email,
                                   String verify) {
        try {
            if (verifyService.doVerify(email, verify) && password.equals(passwordRepeat)) {
                User user = new User();
                user.setUserName(username);
                Boolean onlyEmail = userInfoService.judgeOnlyEmail(email);
                user.setEmail(email);
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

                //1：通过MQ来完成消息的分表
                //参数1：交换机。 参数2：路由key/queue队列名称。 参数3：消息内容
                rabbitTemplate.convertAndSend(RabbitMqConstant.EXCHANGE, RabbitMqConstant.EMAIL_ROUTING_KEY, email);

                return new ResponseResult(HttpStatus.SUCCESS, "用户:" + email + "注册成功!");
            } else {
                return new ResponseResult(HttpStatus.BAD_REQUEST, "注册失败，请检查邮箱验证码或是密码是是否输入正确");
            }
        } catch (Exception e) {
            return new ResponseResult(HttpStatus.UNAUTHORIZED, "发生未知错误!");
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
        return new ResponseResult(HttpStatus.SUCCESS, "退出成功");
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
                return new ResponseResult(HttpStatus.SUCCESS, "更新密码成功！");
            } else {
                return new ResponseResult(HttpStatus.BAD_REQUEST, "更新密码失败！");
            }
        } else {
            return new ResponseResult(HttpStatus.BAD_REQUEST, "修改密码失败！请检查密码是否输入正确！");
        }
    }
}
