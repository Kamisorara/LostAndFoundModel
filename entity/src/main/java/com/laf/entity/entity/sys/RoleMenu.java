package com.laf.entity.entity.sys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * (RoleMenu)表实体类
 *
 * @author Kamisora
 * @since 2022-06-25 10:40:39
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName(value= "sys_roleMenu")
public class RoleMenu {
    //角色id

    private Long roleId;
    //权限id
    private Long menuId;

}

