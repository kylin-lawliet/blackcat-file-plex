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
* 视频演员关联
* </p>
*
* @author zhangdahui 2025-03-13
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class VideoPerformer extends Model<VideoPerformer> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
    * 扫描信息ID
    */
    private Long videoId;

    /**
    * 演员TMDB的ID
    */
    private String performerId;

    /**
     * 演员名称
     */
    @TableField(exist = false)
    private String performerName;

    /**
     * 原始名称
     */
    @TableField(exist = false)
    private String originalName;

    /**
    * 角色
    */
    private String characterName;

    /**
    * 排序
    */
    private Integer orderNumber;

    /**
     * 海报
     */
    @TableField(exist = false)
    private String posterPath;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}