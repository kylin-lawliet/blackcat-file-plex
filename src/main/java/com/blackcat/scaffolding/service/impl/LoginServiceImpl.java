package com.blackcat.scaffolding.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.dao.SysUserMapper;
import com.blackcat.scaffolding.entity.SysUser;
import com.blackcat.scaffolding.model.LoginRequest;
import com.blackcat.scaffolding.model.LoginUser;
import com.blackcat.scaffolding.service.LoginService;
import com.blackcat.scaffolding.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : zhangdahui  2025/2/24 上午9:42
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysUserService userService;

    @Override
    public AjaxResult login(LoginRequest login) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_name",login.getLoginName());
        queryWrapper.eq("password",login.getPassword());
        SysUser sysUser = userMapper.selectOne(queryWrapper);
        System.out.println("登录用户："+login.getLoginName()+"  登录密码："+login.getPassword());
        if (sysUser == null) {
            return AjaxResult.error("用户不存在或密码错误");
        }
        LoginUser user = new LoginUser();
        user.setUserInfo(sysUser);
        return AjaxResult.success(user);
    }
}
