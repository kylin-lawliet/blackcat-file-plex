package com.blackcat.nas.controller;


import com.blackcat.nas.common.annotation.Log;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.entity.SysUser;
import com.blackcat.nas.enums.BusinessType;
import com.blackcat.nas.model.PasswordChangeRequest;
import com.blackcat.nas.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p> 用户表 前端控制器
 * @author zhangdahui 2025-02-24
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @PutMapping("/changePassword/{userId}")
    public AjaxResult changePassword(@PathVariable String userId,@RequestBody PasswordChangeRequest request) {
        System.out.println("oldPassword"+request.getOldPassword());
        System.out.println("newPassword"+request.getNewPassword());
        return userService.updatePassword(userId,request.getOldPassword(),request.getNewPassword());
    }

    @GetMapping("/selectPage")
    public AjaxResult selectPage(Integer page, Integer size, String userName,String loginName) {
        return userService.selectPage(page,size,userName,loginName);
    }

    @Log(title = "编辑", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody SysUser obj) {
        return userService.edit(obj);
    }

    @GetMapping("/getOne")
    public AjaxResult getOne(String id) {
        return AjaxResult.success(userService.getById(id));
    }

    @Log(title = "删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    public AjaxResult delete(String id) {
        userService.removeById(id);
        return AjaxResult.success();
    }

    @GetMapping("/getSelectList")
    public AjaxResult getSelectList(String id) {
        return AjaxResult.success(userService.getById(id));
    }


}
