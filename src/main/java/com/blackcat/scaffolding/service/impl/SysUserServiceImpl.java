package com.blackcat.scaffolding.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.common.result.CustomPage;
import com.blackcat.scaffolding.dao.SysUserMapper;
import com.blackcat.scaffolding.entity.SysUser;
import com.blackcat.scaffolding.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

import static com.blackcat.scaffolding.constant.Constant.VALID;


/**
 * <p> 用户表 服务实现类
 * @author zhangdahui 2025-02-24
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public AjaxResult updatePassword(String userId, String oldPassword, String newPassword) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("password",oldPassword);
        Long count = userMapper.selectCount(queryWrapper);
        if (count == 0) {
            return AjaxResult.error("旧密码不正确");
        }
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", userId);
        updateWrapper.set("password", newPassword);
        userMapper.update(updateWrapper);
        return AjaxResult.success();
    }

    @Override
    public AjaxResult selectPage(Integer pageNow, Integer pageSize, String userName,String loginName) {
        Page<SysUser> page = new Page<>(pageNow, pageSize);
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userName)) {
            queryWrapper.like("user_name", userName);
        }
        if (StringUtils.isNotBlank(loginName)) {
            queryWrapper.like("login_name", loginName);
        }
        Page<SysUser> result = userMapper.selectPage(page, queryWrapper);
        return AjaxResult.success(CustomPage.mybatisPage(result));
    }

    @Override
    public AjaxResult edit(SysUser obj) {
        // TODO 未做本地用户信息存储
        obj.setCreateBy("1");
        obj.setCreateTime(LocalDateTime.now());
        obj.setValidStatus(VALID);
        if (obj.getUserId() == null) {
            userMapper.insert(obj);
        }else {
            userMapper.updateById(obj);
        }
        return AjaxResult.success();
    }
}
