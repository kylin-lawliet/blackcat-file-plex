package com.blackcat.scaffolding.model;

import com.blackcat.scaffolding.entity.SysUser;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : zhangdahui  2025/2/22 下午2:31
 */
@Data
@NoArgsConstructor
public class LoginUser {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 登录名称
     */
    private String loginName;
    /**
     * 用户信息
     */
    private SysUser userInfo;
    /**
     * 用户唯一标识
     */
    private String token;

}
