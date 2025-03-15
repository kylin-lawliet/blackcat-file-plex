package com.blackcat.nas.controller;


import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import com.blackcat.nas.service.VideoScanService;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> 视频数据 前端控制器
 * @author zhangdahui 2025-03-06
 */
@RestController
@RequestMapping("/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @GetMapping("/selectPage")
    public AjaxResult selectPage(Integer page, Integer size,String name,String category) {
        return videoService.selectLibraryPage(page,size,name,category);
    }

    @GetMapping("/getOne")
    public AjaxResult getOne(String id) {
        return AjaxResult.success();
    }

    @GetMapping("/scanVideo")
    public AjaxResult scanVideo() {
        return videoService.scanVideo();
    }

    @GetMapping("/scanVideoByPath")
    public AjaxResult scanVideoByPath(String filePath,String type) {
        return videoService.scanVideoByPath(filePath,type,null,null);
    }

    @GetMapping("/scrapeMetadataById")
    public AjaxResult scrapeMetadataById(String id,String tmdbId) {
        return videoService.scrapeMetadata(id,tmdbId);
    }

    @GetMapping("/scrapeAllMetadata")
    public AjaxResult scrapeAllMetadata() {
        return videoService.scrapeAllMetadata();
    }
}
