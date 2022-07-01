package com.laf.entity.entity.tokenResp;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserResp {

    private Long id;

    private String userName;

    private String avatarUrl;


}
