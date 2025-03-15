package com.blackcat.nas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.common.result.CustomPage;
import com.blackcat.nas.entity.SysUser;
import com.blackcat.nas.entity.VideoEpisodes;
import com.blackcat.nas.dao.VideoEpisodesMapper;
import com.blackcat.nas.entity.VideoSeasons;
import com.blackcat.nas.service.VideoEpisodesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p> 视频剧集信息 服务实现类
 * @author zhangdahui 2025-03-14
 */
@Service
public class VideoEpisodesServiceImpl extends ServiceImpl<VideoEpisodesMapper, VideoEpisodes> implements VideoEpisodesService {

    @Autowired
    private VideoEpisodesMapper episodesMapper;

    @Override
    public AjaxResult selectPage(Integer pageNow, Integer pageSize, String videoName, Integer seasonsNumber, Integer episodeNumber) {
        List<VideoEpisodes> list = episodesMapper.selectPage((pageNow - 1) * pageSize, pageSize,videoName,seasonsNumber,episodeNumber);
        int count = episodesMapper.selectPageCount(videoName,seasonsNumber,episodeNumber);
        return AjaxResult.success(new CustomPage(list,count,pageNow,pageSize));
    }

    @Override
    public AjaxResult edit(VideoEpisodes obj) {
        if (obj.getId() == null) {
            episodesMapper.insert(obj);
        }else {
            episodesMapper.updateById(obj);
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult getOne(String id) {
        return AjaxResult.success(episodesMapper.getOne(id));
    }
}
