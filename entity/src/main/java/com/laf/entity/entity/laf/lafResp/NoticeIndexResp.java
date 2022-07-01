package com.laf.entity.entity.laf.lafResp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class NoticeIndexResp {
    private Long id;

    private Long userId;

    private String lafPhotoUrl;

}
