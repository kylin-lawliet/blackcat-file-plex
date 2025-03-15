package com.blackcat.nas.utils;
import com.blackcat.nas.model.VideoScanFile;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述 :   从路径截取视频名称
 * @author : zhangdahui 2025/3/8 下午6:27
*/
public class VideoNameExtractor {

    /**
     * 描述 :   从文件路径中提取视频名称
     * @author : zhangdahui 2025/3/8 下午6:28
     * @param filePath  文件路径
    */
    public static VideoScanFile extractVideoName(String filePath) {
        String videoPath = getVideoParentDirectory(filePath);
        // 获取文件名（去除路径和扩展名）
        String fileName = new File(filePath).getName();
//        System.out.println("fileName："+fileName);
        if (fileName.lastIndexOf(".") == -1) {
            return null;
        }
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        // 尝试从文件名中提取影视名称
        String videoName = extractNameFromFileName(fileName);
        if (videoName != null) {
            return new VideoScanFile(videoName,filePath,videoPath);
        }

        // 如果文件名中不包含影视名称，从父目录名中提取
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null) {
            String parentDirName = parentDir.getName();
            // 检查父目录名是否为季数目录（如 S1, S2 等），如果是则继续往上找父目录
            if (parentDirName.matches("S\\d+")) {
                File grandParentDir = parentDir.getParentFile();
                if (grandParentDir != null) {
                    parentDirName = grandParentDir.getName();
                }
            }
            videoName = extractNameFromDirectoryName(parentDirName);
            if (videoName != null) {
                return new VideoScanFile(videoName,filePath,videoPath);
            }
        }

        // 如果仍然无法提取，返回文件名
        return new VideoScanFile(videoName,filePath,videoPath);
    }

    /**
     * 描述 :   从文件名中提取影视名称
     * @author : zhangdahui 2025/3/8 下午6:28
     * @param fileName  文件名称
    */
    private static String extractNameFromFileName(String fileName) {
        // 规则：去除季数（S1）、集数（E1）、分辨率（1080p、4k）、年份（2009）等无关信息
        String regex = "^([^\\d._\\s-]+(?:[\\s-][^\\d._\\s-]+)*)(?:[._\\s-]*(?:[Ss]\\d+[Ee]\\d+|\\d{4}|(1080|720|2160)[pi]|4k))*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileName);

        if (matcher.find()) {
            String name = matcher.group(1);
            // 去除末尾的特殊字符（如空格、点号等）
            name = name.replaceAll("[\\s.]+$", "");
            if (!name.isEmpty()) {
//                System.out.println("extractNameFromFileName " + name);
                return name;
            }
        }
//        System.out.println("extractNameFromFileName null");
        return null;
    }

    /**
     * 描述 :   从目录名中提取影视名称
     * @author : zhangdahui 2025/3/8 下午6:29
     * @param dirName  目录名
    */
    private static String extractNameFromDirectoryName(String dirName) {
        // 规则：去除年份（如 (2014)）等无关信息
        String regex = "^([^\\(]+)(?:\\s*\\(\\d{4}\\))?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(dirName);

        if (matcher.find()) {
            String name = matcher.group(1);
            // 去除末尾的特殊字符（如空格、点号等）
            name = name.replaceAll("[\\s.]+$", "");
            if (!name.isEmpty()) {
//                System.out.println("extractNameFromDirectoryName " + name);
                return name;
            }
        }
//        System.out.println("extractNameFromDirectoryName null");
        return null;
    }

    /**  
     * 描述 :   获取视频根路径
     * @author : zhangdahui 2025/3/9 上午11:47
     * @param filePath  全路径
    */
    public static String getVideoParentDirectory(String filePath) {
        File file = new File(filePath);
        // 获取文件的父目录
        File parentFile = file.getParentFile();
        // 如果父目录名称是季数相关（如 S1、第一季），则继续获取上一级父目录
        while (parentFile != null && (parentFile.getName().matches("S\\d+") || parentFile.getName().matches("第.*季"))) {
            parentFile = parentFile.getParentFile();
        }
        if (parentFile != null) {
            return parentFile.getAbsolutePath();
        }
        return null;
    }

    public static void main(String[] args) {
        String[] filePaths = {
                "/Volumes/media/link/tv/日韩剧/非自然死亡(2018)/S1/非自然死亡.S1.E5.死的报复.1080p.mkv",
                "/Volumes/media/link/movie/华语电影/周处除三害(2023)/周处除三害.2023.1080p.mkv",
                "/Volumes/media/link/movie/华语电影/扫毒(2013)/扫毒.2013.2160p.mp4",
                "/Volumes/media/link/movie/日番动画电影/名侦探柯南/名侦探柯南：灰原哀物语 黑铁的神秘列车(2023)/名侦探柯南：灰原哀物语 黑铁的神秘列车.2023.1080p.mkv"
        };

        for (String filePath : filePaths) {
            String videoName = extractVideoName(filePath).getFileName();
            System.out.println("文件路径: " + filePath);
            System.out.println("视频名称: " + videoName);
            System.out.println("------");
        }
    }
}