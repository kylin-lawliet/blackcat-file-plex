package com.blackcat.nas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
* <p>
* 剧集详细信息
* </p>
*
* @author zhangdahui 2025-03-13
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VideoSeasons extends Model<VideoSeasons> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 季名称
    */
    private String seasonsName;

    /**
    * 季目录
    */
    private String filePath;

    /**
    * 季数
    */
    private Integer seasonsNumber;

    /**
    * 系列名称
    */
    private String seriesName;

    /**
    * 集数
    */
    private Integer episodeCount;

    /**
    * 简介
    */
    private String introduction;

    /**
    * 播出时间
    */
    private String broadcastTime;

    /**
     * 海报
     */
    @TableField(exist = false)
    private String posterPath;

    /**
     * TMDB网站信息ID
     */
    private String tmdbId;

    /**
     * 扫描表ID
     */
    private Long videoId;
    @TableField(exist = false)
    private String videoName;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}