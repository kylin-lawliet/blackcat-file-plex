package com.blackcat.nas.service;

import com.blackcat.nas.common.result.AjaxResult;

/**
 * <p> 视频数据 服务类
 * @author zhangdahui 2025-03-06
 */
public interface VideoService {

    AjaxResult getVideoDetail(String id,String showType);

    /**
     * 描述 :   刮削所有数据
     * @author : zhangdahui 2025/3/14 下午5:48
    */
    AjaxResult scrapeAllMetadata();

    /**
     * 描述 :   刮削视频元数据
     * @author : zhangdahui 2025/3/10 下午5:35
     */
    AjaxResult scrapeMetadata(String id,String tmdbId);

    /**
     * 描述 :   扫描所有路径视频文件
     * @author : zhangdahui 2025/3/10 下午5:32
     */
    AjaxResult scanVideo();

    /**
     * 描述 :   根据路径扫描视频文件
     * @author : zhangdahui 2025/3/10 下午5:32
     * @param filePath 路径
     * @param type  视频类型
     */
    AjaxResult scanVideoByPath(String filePath,String type, String scanFilterDirectory, String scanVideoType);

    /**
     * 描述 :  媒体库查询
     * @author : zhangdahui 2025/2/24 上午10:28
     * @param pageNow 当前页
     * @param pageSize 每页数量
     */
    AjaxResult selectLibraryPage(Integer pageNow, Integer pageSize,String name,String category);
}
