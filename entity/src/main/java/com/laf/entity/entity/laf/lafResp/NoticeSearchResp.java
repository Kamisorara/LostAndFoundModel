package com.laf.entity.entity.laf.lafResp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 搜索启示回复类
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NoticeSearchResp {
    private Long id;

    private Long userId;

    private String message;

    private String userName;

    private String lafPhotoUrl;

    //启示类型(0寻物启事，1拾物启示)
    private String type;

    //用户头像url
    private String avatarUrl;

    private List<String> lafPhotoUrls;

    //启示浏览次数
    private Integer view;

    //启示是否已完成(0完成，1未完成)
    private String done;
}
