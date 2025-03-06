package com.blackcat.nas.service;

import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.model.LoginRequest;

/**
 * @author : zhangdahui  2025/2/24 上午9:42
 */
public interface LoginService {

    AjaxResult login(LoginRequest login);
}
