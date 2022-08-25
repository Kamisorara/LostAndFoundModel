package com.laf.entity.entity.sys;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;


/**
 * (sys_info)表实体类
 *
 * @author Kamisora
 * @since 2022-08-19 12:51:31
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value = "sys_info")
public class Info {
    //消息id
    private Long id;
    //用户id
    private Long userId;
    //消息主体信息
    private String messageInfo;
    //显示是否删除状态
    private String status;
    //是否阅读状态
    private String read;
    //info创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
}

