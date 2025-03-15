package com.blackcat.nas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
* <p>
* 演员信息表
* </p>
*
* @author zhangdahui 2025-03-13
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Performer extends Model<Performer> {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String tmdbId;

    /**
    * 演员名称
    */
    private String name;

    /**
    * 原始名称
    */
    private String originalName;

    /**
    * 演员图片地址
    */
    private String profilePath;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}