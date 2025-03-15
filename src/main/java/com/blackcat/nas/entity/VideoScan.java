package com.blackcat.nas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
* <p>
* 视频扫描数据
* </p>
*
* @author zhangdahui 2025-03-06
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VideoScan extends Model<VideoScan> {

    @TableId(value = "video_id", type = IdType.AUTO)
    private Long videoId;

    /**
    * 视频名称
    */
    private String videoName;

    /**
     * 原始视频名称
     */
    private String originalTitle;

    /**
     * 系列名称
     */
    private String seriesName;

    /**
     * 视频分类
     */
    private String videoCategory;

    /**
     * 视频分类目录
     */
    private String videoDirectory;


    /**
    * 视频文件路径
    */
    private String videoPath;

    /**
     * 转换后的访问路径
     */
    @TableField(exist = false)
    private String httpUrl;

    /**
    * 播出时间
    */
    private String broadcastTime;

    /**
    * 用户评分
    */
    private String userScore;

    /**
    * 剧集或电影
    */
    private String videoType;

    /**
    * TMDB网站信息ID
    */
    private String tmdbId;

    /**
     * IMDB网站信息ID
     */
    private String imdbId;

    /**
     * IMDB网站信息ID
     */
    private String tvdbId;

    /**
    * 分辨率
    */
    private String resolution;

    /**
     * 视频简介
     */
    private String introduction;

    /**
     * 系列ID
     */
    private String collectionId;

    /**
     * 年份
     */
    private String year;

    /**
     * 播放时长
     */
    private String runTime;

    /**
     * 季数
     */
    private String seasons;

    /**
     * 是否已刮削数据
     */
    private boolean scrape;


    @TableField(exist = false)
    private String profilePath;
    @TableField(exist = false)
    private String backdropPath;
    @TableField(exist = false)
    List<VideoSeasons> videoSeasonsList;
    @TableField(exist = false)
    List<VideoPerformer> videoPerformerList;

    @Override
    public Serializable pkVal() {
        return this.videoId;
    }


}