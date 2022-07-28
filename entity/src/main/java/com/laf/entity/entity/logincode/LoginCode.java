package com.laf.entity.entity.logincode;

import com.laf.entity.enums.LoginCodeEnum;
import lombok.Data;

/**
 * 登录验证码类型
 */
// fhadmin.cn
@Data
public class LoginCode {

    /**
     * 验证码配置
     */
    private LoginCodeEnum codeType;
    /**
     * 验证码有效期 分钟
     */
    private Long expiration = 2L;
    /**
     * 验证码内容长度
     */
    private int length = 2;
    /**
     * 验证码宽度
     */
    private int width = 111;
    /**
     * 验证码高度
     */
    private int height = 36;
    /**
     * 验证码字体
     */
    private String fontName;
    /**
     * 字体大小
     */
    private int fontSize = 25;

    /**
     * 验证码前缀
     *
     * @return
     */
    private String codeKey;


    public LoginCodeEnum getCodeType() {
        return codeType;
    }
}
