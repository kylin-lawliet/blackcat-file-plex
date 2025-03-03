package com.blackcat.scaffolding.model;

import lombok.Data;

/**
 * @author : zhangdahui  2025/2/28 下午5:38
 */
@Data
public class PasswordChangeRequest {

    private String oldPassword;
    private String newPassword;
}
