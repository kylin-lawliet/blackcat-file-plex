package com.blackcat.nas.controller;

import com.blackcat.nas.common.annotation.Log;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.entity.VideoScan;
import com.blackcat.nas.enums.BusinessType;
import com.blackcat.nas.service.VideoScanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : zhangdahui  2025/3/10 下午5:42
 */
@RestController
@RequestMapping("/scan")
public class VideoScanController {

    @Autowired
    private VideoScanService videoVideoScanService;

    @GetMapping("/selectPage")
    public AjaxResult selectPage(Integer page, Integer size, String videoName, String seriesName, String videoType) {
        return videoVideoScanService.selectPage(page,size,videoName,seriesName,videoType);
    }

    @Log(title = "编辑", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody VideoScan obj) {
        return videoVideoScanService.edit(obj);
    }

    @GetMapping("/getOne")
    public AjaxResult getOne(String id) {
        return AjaxResult.success(videoVideoScanService.getById(id));
    }

    @Log(title = "删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    public AjaxResult delete(String id) {
        videoVideoScanService.removeById(id);
        return AjaxResult.success();
    }
}
