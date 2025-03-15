package com.blackcat.nas.controller;


import com.blackcat.nas.common.annotation.Log;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.entity.SysUser;
import com.blackcat.nas.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.blackcat.nas.service.VideoSeasonsService;
import com.blackcat.nas.entity.VideoSeasons;

/**
 * <p> 季信息 前端控制器
 * @author zhangdahui 2025-03-13
 */
@RestController
@RequestMapping("/seasons")
public class VideoSeasonsController {

    @Autowired
    private VideoSeasonsService seasonsService;

    @GetMapping("/selectPage")
    public AjaxResult selectPage(Integer page, Integer size, String videoName, Integer seasonsNumber) {
        return seasonsService.selectPage(page,size,videoName,seasonsNumber);
    }

    @Log(title = "编辑", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody VideoSeasons obj) {
        return seasonsService.edit(obj);
    }

    @GetMapping("/getOne")
    public AjaxResult getOne(String id) {
        return seasonsService.getOne(id);
    }

    @Log(title = "删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    public AjaxResult delete(String id) {
        seasonsService.removeById(id);
        return AjaxResult.success();
    }
}
