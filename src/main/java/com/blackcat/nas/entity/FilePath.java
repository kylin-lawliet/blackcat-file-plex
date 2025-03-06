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
* 文件路径配置
* </p>
*
* @author zhangdahui 2025-03-06
*/
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FilePath extends Model<FilePath> {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
    * 路径名称
    */
    private String pathName;

    /**
    * 路径地址
    */
    private String filePath;

    /**
    * 文件类型：1-电影，2-电视剧，3-漫画，4-电子书，5-有声书，6-音乐，7-MV
    */
    private Integer fileType;

    @Override
    public Serializable pkVal() {
        return this.id;
    }
}