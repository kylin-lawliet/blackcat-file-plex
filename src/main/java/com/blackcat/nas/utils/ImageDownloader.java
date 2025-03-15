package com.blackcat.nas.utils;
import com.blackcat.nas.service.impl.VideoScanServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * 下载图片
 * @author : zhangdahui  2025/3/13 下午2:08
 */
public class ImageDownloader {

    private static final Logger log = LoggerFactory.getLogger(ImageDownloader.class);
    /**
     * 描述 :   下载图片
     * @author : zhangdahui 2025/3/13 下午2:12
     * @param imageUrl 图片原链接
     * @param savePath 图片保存地址
    */
    public static void downloadImage(String imageUrl, String savePath) throws IOException {
        // 创建 URL 对象
        URL url = new URL(imageUrl);
        // 打开连接
        URLConnection connection = url.openConnection();
        // 获取输入流
        InputStream inputStream = connection.getInputStream();
        // 创建文件输出流
        FileOutputStream outputStream = new FileOutputStream(savePath);
        // 缓冲区大小
        byte[] buffer = new byte[4096];
        int bytesRead;
        // 循环读取输入流并写入输出流
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        // 关闭流
        inputStream.close();
        outputStream.close();
    }

    /**
     * 描述 : 下载TMDB网站图片
     * @author : zhangdahui 2025/3/13 下午3:40
     * @param api TMDB网站图片API
     * @param type 下载图片大小类型
     * @param imageUrl 图片地址
     * @param savePath 保存路径
     * @param fileName 保存名称
    */
    public static void downloadTmdbImage(String api,String type,String imageUrl, String savePath,String fileName) {
        if(StringUtils.isNotBlank(imageUrl)){
            String url = api + type + imageUrl;
            savePath = savePath + fileName;
            try {
                downloadImage(url, savePath);
                log.info("TMDB 图片下载成功：{}", savePath);
            } catch (IOException e) {
                log.error("TMDB 图片下载失败：", e);
            }
        }
    }

    public static void main(String[] args) {
        String imageUrl = "https://image.tmdb.org/t/p/w185/8bwYSkseRMrwZxD9pS9mQlS0bDR.jpg";
        String savePath = "/Users/blackcat/Documents/file-plex/performer/谢苗.jpg";
        try {
            downloadImage(imageUrl, savePath);
            System.out.println("图片下载成功！");
        } catch (IOException e) {
            System.err.println("图片下载失败：" + e.getMessage());
        }
    }
}
