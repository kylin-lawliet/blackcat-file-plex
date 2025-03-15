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
* 视频剧集信息
* </p>
*
* @author zhangdahui 2025-03-14
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VideoEpisodes extends Model<VideoEpisodes> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 播出时间
    */
    private String broadcastTime;

    /**
    * 集数
    */
    private Integer episodeNumber;

    /**
    * 集名称
    */
    private String name;

    /**
    * 简介
    */
    private String introduction;

    /**
    * 时长
    */
    private String runtime;

    /**
    * 季数
    */
    private Integer seasonNumber;

    /**
     * 剧集ID
     */
    private String showId;


    @TableField(exist = false)
    private String videoName;
    @TableField(exist = false)
    private String seasonsName;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}