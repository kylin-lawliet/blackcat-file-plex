package com.blackcat.nas.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.common.result.CustomPage;
import com.blackcat.nas.entity.SysUser;
import com.blackcat.nas.entity.VideoScan;
import com.blackcat.nas.entity.VideoSeasons;
import com.blackcat.nas.dao.VideoSeasonsMapper;
import com.blackcat.nas.service.VideoSeasonsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p> 剧集详细信息 服务实现类
 * @author zhangdahui 2025-03-13
 */
@Service
public class VideoSeasonsServiceImpl extends ServiceImpl<VideoSeasonsMapper, VideoSeasons> implements VideoSeasonsService {

    @Autowired
    private VideoSeasonsMapper seasonsMapper;

    @Override
    public AjaxResult selectPage(Integer pageNow, Integer pageSize, String videoName, Integer seasonsNumber) {
        List<VideoSeasons> list = seasonsMapper.selectPage((pageNow - 1) * pageSize, pageSize,videoName,seasonsNumber);
        int count = seasonsMapper.selectCount(videoName,seasonsNumber);
        return AjaxResult.success(new CustomPage(list,count,pageNow,pageSize));
    }

    @Override
    public AjaxResult edit(VideoSeasons obj) {
        if (obj.getId() == null) {
            seasonsMapper.insert(obj);
        }else {
            seasonsMapper.updateById(obj);
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult getOne(String id) {
        return AjaxResult.success(seasonsMapper.getOne(id));
    }
}
