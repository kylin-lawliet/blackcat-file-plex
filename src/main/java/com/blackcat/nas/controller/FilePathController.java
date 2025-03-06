package com.blackcat.nas.controller;


import com.blackcat.nas.common.annotation.Log;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.entity.SysUser;
import com.blackcat.nas.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.blackcat.nas.service.FilePathService;
import com.blackcat.nas.entity.FilePath;

/**
 * <p> 文件路径配置 前端控制器
 * @author zhangdahui 2025-03-06
 */
@RestController
@RequestMapping("/path")
public class FilePathController {

    @Autowired
    private FilePathService filePathService;

    @GetMapping("/selectPage")
    public AjaxResult selectPage(Integer page, Integer size, String userName, String loginName) {
        return filePathService.selectPage(page,size);
    }

    @Log(title = "编辑", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody FilePath obj) {
        return filePathService.edit(obj);
    }

    @GetMapping("/getOne")
    public AjaxResult getOne(String id) {
        return AjaxResult.success(filePathService.getById(id));
    }

    @Log(title = "删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    public AjaxResult delete(String id) {
        filePathService.removeById(id);
        return AjaxResult.success();
    }
}
