package com.laf.web;

import com.laf.dao.mapper.MenuMapper;
import com.laf.dao.mapper.UserMapper;
import com.laf.dao.mapper.laf.NoticeMapper;
import com.laf.dao.mapper.laf.lafPhotosMapper;
import com.laf.entity.entity.laf.lafResp.NoticeIndexResp;
import com.laf.entity.entity.laf.lafResp.NoticeSearchResp;
import com.laf.entity.entity.sys.User;
import com.laf.service.service.PersonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;


@SpringBootTest
class WebApplicationTests {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private lafPhotosMapper lafPhotosMapper;

    @Test
    void contextLoads() {
        User user = new User();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123123");
        user.setUserName("Kamisora");
        user.setPassword(encode);
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

    @Test
    void getCreatedUserId() {
        NoticeSearchResp noticeDetail = noticeMapper.getNoticeDetail(3L);
        System.out.println(noticeDetail.toString());
    }

    @Test
    void indexDisplay() {
        Integer integer = lafPhotosMapper.countIndexDisplayPhoto(3L);
        System.out.println(integer);
    }
}
