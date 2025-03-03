package com.blackcat.scaffolding.controller;

import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.model.LoginRequest;
import com.blackcat.scaffolding.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : zhangdahui  2025/2/28 下午4:10
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
   private LoginService loginService;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginRequest login) {
        return loginService.login(login);
    }
}
