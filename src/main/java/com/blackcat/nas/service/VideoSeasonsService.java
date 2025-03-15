package com.blackcat.nas.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.entity.VideoSeasons;

/**
 * <p> 剧集详细信息 服务类
 * @author zhangdahui 2025-03-13
 */
public interface VideoSeasonsService extends IService<VideoSeasons> {

    /**
     * 描述 :  分页查询
     * @author : zhangdahui 2025/2/24 上午10:28
     * @param pageNow 当前页
     * @param pageSize 每页数量
     */
    AjaxResult selectPage(Integer pageNow, Integer pageSize, String videoName, Integer seasonsNumber);

    /**
     * 描述 :   新增或编辑
     * @author : zhangdahui 2025/2/24 上午10:27
     * @param obj  数据对象
     */
    AjaxResult edit(VideoSeasons obj);

    /**
     * 描述 :   获取详情
     * @author : zhangdahui 2025/3/14 下午4:38
     * @param id  主键
    */
    AjaxResult getOne(String id);
}
