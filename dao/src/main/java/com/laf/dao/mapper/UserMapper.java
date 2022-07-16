package com.laf.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laf.entity.entity.resp.userResp;
import com.laf.entity.entity.sys.User;
import com.laf.entity.entity.tokenResp.UserDetailInfoResp;
import com.laf.entity.entity.tokenResp.UserResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface UserMapper extends BaseMapper<User> {

    //根据用户名查找用户id （用户注册时查找新增用户id用）
    Long selectUserIdByUserName(String userName);

    //根据id获取用户状态
    String getUserStatus(Long id);

    //根据根据id获取用户旧(加密)密码
    String getUserOldPass(Long userId);

    //根据用户id更新密码
    Integer updateUserPassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);

    //根据用户id更新用户详情
    Integer updateUserInfoById(@Param("userId") Long userId, @Param("userName") String userName, @Param("phoneNumber") String phoneNumber);

    //根据用户id获取用户详情
    UserResp getUserInfoByToken(Long id);

    //根据用户id获取用户更加详细信息包括包括用户帮助次数，用户个人背景图片等
    UserDetailInfoResp getUserDetailInfo(Long id);

    //根据用户id 获取用户昵称，头像
    userResp getUserNameAndAvatarByUserId(Long userId);
}
