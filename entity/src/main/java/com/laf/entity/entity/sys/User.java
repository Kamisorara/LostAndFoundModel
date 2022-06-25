package com.laf.entity.entity.sys;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (User)表实体类
 *
 * @author Kamisora
 * @since 2022-06-25 10:38:12
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value= "sys_user")
public class User {
    //用户id
    private Long id;
    //用户名
    private String userName;
    //用户手机号
    private String phoneNumber;
    //用户邮箱号
    private String email;
    //用户头像url
    private String avatar;
    //用户启用状态(0启用，1禁用)
    private String status;
    //账号创建时间
    private Date createTime;
    //密码
    private String password;

}

