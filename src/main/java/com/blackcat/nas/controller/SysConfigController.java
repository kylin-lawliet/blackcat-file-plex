package com.blackcat.nas.controller;


import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.service.SysConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 配置表 前端控制器
 * @author zhangdahui 2025-03-06
 */
@RestController
@RequestMapping("/config")
public class SysConfigController {

    @Autowired
    private SysConfigService service;


}
