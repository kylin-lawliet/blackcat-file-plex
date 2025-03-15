package com.blackcat.nas.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 视频信息
 * @author : zhangdahui  2025/3/7 下午4:55
 */
@Data
@NoArgsConstructor
public class VideoMain {

    /**
     * 名称
     */
   private String name;

    /**
     * 背景图
     */
    private String backdropPath;

    /**
     * 上线日期
     */
    private String releaseDate;

    /**
     * 类型
     */
    private String genres;

}
