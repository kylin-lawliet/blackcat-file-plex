package com.blackcat.scaffolding.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户登录对象
 * @author : zhangdahui  2025/2/22 下午2:25
 */
@Data
@NoArgsConstructor
public class LoginRequest {
    /**
     * 登录名称
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    /**
     * 唯一标识
     */
    private String uuid;
}
