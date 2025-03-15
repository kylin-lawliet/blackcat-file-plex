package com.blackcat.nas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.common.result.CustomPage;
import com.blackcat.nas.dao.*;
import com.blackcat.nas.entity.*;
import com.blackcat.nas.service.VideoScanService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> 视频数据 服务实现类
 * @author zhangdahui 2025-03-06
 */
@Service
public class VideoScanServiceImpl extends ServiceImpl<VideoScanMapper, VideoScan> implements VideoScanService {

    private static final Logger log = LoggerFactory.getLogger(VideoScanServiceImpl.class);

    @Autowired
    private VideoScanMapper videoMapper;

    @Override
    public AjaxResult selectPage(Integer pageNow, Integer pageSize, String videoName, String seriesName, String videoType) {
        Page<VideoScan> page = new Page<>(pageNow, pageSize);
        QueryWrapper<VideoScan> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(videoName)) {
            queryWrapper.like("video_name", videoName);
        }
        if (StringUtils.isNotBlank(seriesName)) {
            queryWrapper.like("series_name", seriesName);
        }
        if (StringUtils.isNotBlank(videoType)) {
            queryWrapper.like("video_type", videoType);
        }
        queryWrapper.orderByAsc("video_name");
        Page<VideoScan> result = videoMapper.selectPage(page, queryWrapper);
        return AjaxResult.success(CustomPage.mybatisPage(result));
    }

    @Override
    public AjaxResult edit(VideoScan obj) {
        if (obj.getVideoId() == null) {
            videoMapper.insert(obj);
        }else {
            videoMapper.updateById(obj);
        }
        return AjaxResult.success();
    }

}
