package com.blackcat.scaffolding.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blackcat.scaffolding.common.result.AjaxResult;
import com.blackcat.scaffolding.entity.SysUser;

/**
 * <p> 用户表 服务类
 * @author zhangdahui 2025-02-24
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 描述 :   修改密码
     * @author : zhangdahui 2025/3/1 上午10:05
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
    */
    AjaxResult updatePassword(String userId, String oldPassword, String newPassword);

    /**
     * 描述 :  分页查询
     * @author : zhangdahui 2025/2/24 上午10:28
     * @param pageNow 当前页
     * @param pageSize 每页数量
     * @param userName  用户名称
     */
    AjaxResult selectPage(Integer pageNow, Integer pageSize,String userName,String loginName);

    /**
     * 描述 :   新增或编辑
     * @author : zhangdahui 2025/2/24 上午10:27
     * @param obj  数据对象
     */
    AjaxResult edit(SysUser obj);
}
