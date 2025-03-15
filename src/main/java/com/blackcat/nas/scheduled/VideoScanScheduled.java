package com.blackcat.nas.scheduled;

import com.blackcat.nas.service.SysConfigService;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.blackcat.nas.constant.Constant.CONFIG_KEY_VIDEO_SCAN_CRON;

/**
 * 视频定时扫描
 * @author : zhangdahui  2025/3/8 下午4:54
 */

@Lazy(false)
@Component
@EnableScheduling
@AutoConfigureAfter(SysConfigService.class)
public class VideoScanScheduled  implements SchedulingConfigurer {

    @Autowired
    private SysConfigService configService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
            System.out.println(LocalDateTime.now()+" 1111111111");
        }, triggerContext -> {// 任务触发，可修改任务的执行周期
           String captureCron = configService.getConfigValue(CONFIG_KEY_VIDEO_SCAN_CRON);
            CronTrigger trigger = new CronTrigger(captureCron);
            return trigger.nextExecutionTime(triggerContext);
        });
    }

    // 支持的视频文件扩展名
    private static final String[] VIDEO_EXTENSIONS = {
            ".mp4", ".mkv", ".avi", ".mov", ".wmv", ".flv", ".webm", ".mpeg", ".mpg", ".3gp", ".ts", ".m4v"
    };

    /**
     * 递归扫描目录，返回所有视频文件路径
     */
    public static List<String> scanVideoFiles(String scanPath) {
        List<String> videoFiles = new ArrayList<>();
        File rootDir = new File(scanPath);
        if (rootDir.exists() && rootDir.isDirectory()) {
            scanDirectory(rootDir, videoFiles);
        }
        for (String videoFile : videoFiles) {
            System.out.println(videoFile);
            String videoName = extractVideoName(videoFile);
            System.out.println(videoName);
        }
        return videoFiles;
    }

    /**
     * 递归扫描目录
     */
    private static void scanDirectory(File directory, List<String> videoFiles) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // 如果是目录，递归扫描
                    scanDirectory(file, videoFiles);
                } else if (isVideoFile(file)) {
                    // 如果是视频文件，添加到结果列表
                    videoFiles.add(file.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 检查文件是否为视频文件
     */
    private static boolean isVideoFile(File file) {
        String fileName = file.getName().toLowerCase();
        for (String extension : VIDEO_EXTENSIONS) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 从文件路径中提取视频名称
     *
     * @param filePath 文件路径
     * @return 视频名称
     */
//    public static String extractVideoName(String filePath) {
//        // 获取文件名（去除路径和扩展名）
//        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
//        System.out.println("fileName "+fileName);
//        // 定义正则表达式，匹配视频名称
//        // 规则：去除季数（S1）、集数（E5）、分辨率（1080p）等无关信息
//        String regex = "(.*?)(\\.[Ss]\\d+)?(\\.[Ee]\\d+)?(\\..*)?";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(fileName);
//        System.out.println("matcher "+matcher);
//        if (matcher.find()) {
//            // 提取视频名称
//            String videoName = matcher.group(1);
//            // 去除末尾的特殊字符（如空格、点号等）
//            videoName = videoName.replaceAll("[\\s.]+$", "");
//            return videoName;
//        }
//        System.out.println("fileName2 "+fileName);
//        // 如果匹配失败，返回原始文件名
//        return fileName;
//    }
//    public static String extractVideoName(String filePath) {
//        // 获取文件名（去除路径和扩展名）
//        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1, filePath.lastIndexOf("."));
//
//        // 定义正则表达式，匹配视频名称
//        // 规则：去除季数（S1）、集数（E5）、分辨率（1080p）等无关信息
//        String regex = "(.*?)(\\.[Ss]\\d+)?(\\.[Ee]\\d+)?(\\..*)?";
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(fileName);
//
//        if (matcher.find()) {
//            // 提取视频名称
//            String videoName = matcher.group(1);
//            // 去除末尾的特殊字符（如空格、点号等）
//            videoName = videoName.replaceAll("[\\s.]+$", "");
//            return videoName;
//        }
//
//        return fileName; // 如果匹配失败，返回原始文件名
//    }
    /**
     * 从文件路径中提取视频名称
     *
     * @param filePath 文件路径
     * @return 视频名称
     */
    public static String extractVideoName(String filePath) {
        // 获取文件名（去除路径和扩展名）
        String fileName = new File(filePath).getName();
        fileName = fileName.substring(0, fileName.lastIndexOf("."));

        // 尝试从文件名中提取影视名称
        String videoName = extractNameFromFileName(fileName);
        if (videoName != null) {
            return videoName;
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
                return videoName;
            }
        }

        // 如果仍然无法提取，返回文件名
        return fileName;
    }

    /**
     * 从文件名中提取影视名称
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
                System.out.println("extractNameFromFileName " + name);
                return name;
            }
        }
        System.out.println("extractNameFromFileName null");
        return null;
    }

    /**
     * 从目录名中提取影视名称
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
                System.out.println("extractNameFromDirectoryName " + name);
                return name;
            }
        }
        System.out.println("extractNameFromDirectoryName null");
        return null;
    }

    public static void main(String[] args) {
        String scanPath = "/Volumes/media/link/tv";
        scanVideoFiles(scanPath);
        String scanPath2 = "/Volumes/media/link/movie";
        scanVideoFiles(scanPath2);
    }

//    public static void main(String[] args) {
//        String scanPath = "/Volumes/media/link/tv";
//        File directory = new File(scanPath);
//        System.out.println("directory " + directory);
//        if (directory.isDirectory()) {
//            File[] files = directory.listFiles();
//            System.out.println("files " + files.length);
//            if (files != null) {
//                for (File file : files) {
//
//                    if (file.isFile() && isVideoFile(file.getName())) {
//                        System.out.println("file.getName()" + file.getName());
////                    MediaInfo mediaInfo = FileNameParser.parse(file.getName());
////                    MediaInfo metadata = tmdbClient.fetchMetadata(mediaInfo.getTitle(), mediaInfo.getYear());
////                    if (metadata != null) {
////                        mediaList.add(metadata);
////                    }
////                }
//                    }
//                }
//            }
//
//        }
//    }
//
//    // 定义正则表达式模式，匹配常见视频扩展名
//    private static final Pattern VIDEO_EXTENSION_PATTERN = Pattern.compile("(?i)\\.(mp4|mkv|avi|flv)$");
//
//    public static boolean isVideoFile(String fileName) {
//        return VIDEO_EXTENSION_PATTERN.matcher(fileName).find();
//    }

//    private static boolean isVideoFile(String fileName) {
//        return fileName.endsWith(".mp4") || fileName.endsWith(".mkv");
//    }
}
