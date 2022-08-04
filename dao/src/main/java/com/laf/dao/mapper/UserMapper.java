package com.laf.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.laf.entity.entity.resp.userResp;
import com.laf.entity.entity.sys.User;
import com.laf.entity.entity.tokenResp.UserDetailInfoResp;
import com.laf.entity.entity.tokenResp.UserEditInfoResp;
import com.laf.entity.entity.tokenResp.UserResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;


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

    //根据用户名userName 查询用户
    userResp searchUserByUserName(String userName);

    //查询邮箱下有多少用户
    Integer searchUserByUserEmail(String emailAddr);

    //查询一个userName下有多少个用户
    Integer countUserByUserName(String userName);

    //根据用户token id 获取该用户的所有基本信息
    UserEditInfoResp getUserEditInfo(Long userTokenId);

    //根据用户id 修改用户userName
    Integer updateUserNameById(@Param("userId") Long userId, @Param("userName") String userName);

    //根据用户id 修改该用户联系方式
    Integer updateUserPhoneNumById(@Param("userId") Long userId, @Param("phoneNum") String phoneNum);
}
