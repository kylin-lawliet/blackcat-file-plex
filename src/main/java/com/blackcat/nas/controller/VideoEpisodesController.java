package com.blackcat.nas.controller;


import com.blackcat.nas.common.annotation.Log;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.entity.SysUser;
import com.blackcat.nas.enums.BusinessType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import com.blackcat.nas.service.VideoEpisodesService;
import com.blackcat.nas.entity.VideoEpisodes;

/**
 * <p> 视频剧集信息 前端控制器
 * @author zhangdahui 2025-03-14
 */
@RestController
@RequestMapping("/episodes")
public class VideoEpisodesController {

    @Autowired
    private VideoEpisodesService episodesService;

    @GetMapping("/selectPage")
    public AjaxResult selectPage(Integer page, Integer size, String videoName, Integer seasonsNumber, Integer episodeNumber) {
        return episodesService.selectPage(page,size,videoName,seasonsNumber,episodeNumber);
    }

    @Log(title = "编辑", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody VideoEpisodes obj) {
        return episodesService.edit(obj);
    }

    @GetMapping("/getOne")
    public AjaxResult getOne(String id) {
        return episodesService.getOne(id);
    }

    @Log(title = "删除", businessType = BusinessType.DELETE)
    @DeleteMapping("/delete")
    public AjaxResult delete(String id) {
        episodesService.removeById(id);
        return AjaxResult.success();
    }
}
