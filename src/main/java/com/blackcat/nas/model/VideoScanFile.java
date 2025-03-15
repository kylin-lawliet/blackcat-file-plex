package com.blackcat.nas.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件扫描结果
 * @author : zhangdahui  2025/3/8 下午6:39
 */
@Data
@NoArgsConstructor
public class VideoScanFile {

    // 视频名称
    private String fileName;
    // 文件全路径
    private String filePath;
    // 视频根目录
    private String videoPath;
    // 视频类型
    private String videoType;

    public VideoScanFile(String fileName, String filePath, String videoPath) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.videoPath = videoPath;
    }
}
