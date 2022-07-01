package com.laf.web;

import com.laf.dao.mapper.MenuMapper;
import com.laf.dao.mapper.UserMapper;
import com.laf.dao.mapper.laf.NoticeMapper;
import com.laf.entity.entity.laf.lafResp.NoticeIndexResp;
import com.laf.entity.entity.sys.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@SpringBootTest
class WebApplicationTests {
    @Autowired
    UserMapper userMapper;

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    NoticeMapper noticeMapper;

    @Test
    void contextLoads() {
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123123");
        user.setUserName("Kamisora").setPassword(encode);
        int insert = userMapper.insert(user);
        System.out.println(insert);
    }

    @Test
    void testUserMenu() {
        List<String> list = menuMapper.selectPermsByUserId(1540533503228669954L);
        System.out.println(list);
    }

    @Test
    void getSimpleList() {
        List<NoticeIndexResp> simpleLostInfo = noticeMapper.getSimpleLostInfo();
        System.out.println(simpleLostInfo);
    }
}
