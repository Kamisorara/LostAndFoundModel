package com.laf.entity.entity.laf.lafResp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 首页启示回复类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor

public class NoticeIndexResp {
    private Long id;

    private Long userId;

    //搜索出来的界面就暂时先加载一张图片
    private String lafPhotoUrl;

    //启示类型(0寻物启事，1拾物启示)
    private String type;

    private String userName;

    //启示浏览次数
    private Integer view;


}
