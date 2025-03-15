package com.blackcat.nas.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 文件服务接口
 * @author : zhangdahui  2025/3/7 上午11:12
 */
@RestController
@RequestMapping("/files")
public class FileController {

    /**
     * 通过文件名获取文件
     */
    @GetMapping(value = "/getImg", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getFile(String fileName,String path, HttpServletResponse response) throws IOException {
        File file = new File(path + File.separator + fileName);
        if (file.exists()) {
            // 将文件写入响应流
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            FileUtils.copyFile(file, response.getOutputStream());
            response.getOutputStream().flush();
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
