package com.blackcat.nas.dao;

import com.blackcat.nas.entity.VideoEpisodes;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.blackcat.nas.entity.VideoSeasons;
import org.apache.ibatis.annotations.Param;

import java.util.List;
/**
 * <p> 视频剧集信息 Mapper 接口
 * @author zhangdahui 2025-03-14
 */
public interface VideoEpisodesMapper extends BaseMapper<VideoEpisodes> {

    /**
     * 描述 :   获取详情
     * @author : zhangdahui 2025/3/14 下午4:38
     * @param id  主键
     */
    VideoEpisodes getOne(String id);

    /**
     * 描述 :   分页查询数量
     * @author : zhangdahui 2025/3/14 下午4:37
     * @param videoName 视频名称
     * @param seasonsNumber 季数
     * @param episodeNumber 集数
     */
    int selectPageCount(@Param("videoName") String videoName, @Param("seasonsNumber") Integer seasonsNumber, @Param("episodeNumber") Integer episodeNumber);

    /**
     * 描述 :   分页查询
     * @author : zhangdahui 2025/3/14 下午4:37
     * @param start 开始条数
     * @param end 每页条数
     * @param videoName 视频名称
     * @param seasonsNumber 季数
     * @param episodeNumber 集数
     */
    List<VideoEpisodes> selectPage(@Param("start") Integer start, @Param("end") Integer end, @Param("videoName") String videoName, @Param("seasonsNumber") Integer seasonsNumber, @Param("episodeNumber") Integer episodeNumber);
}
