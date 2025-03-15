package com.blackcat.nas.constant;

/**
 * 通用常量信息
 * @author : zhangdahui  2024/8/23 上午9:18
 */
public class Constant {

    /**
     * 配置键值：是否合并
     */
    public static final String CONFIG_KEY_MERGE = "is_merge";
    /**
     * 配置键值：TMDB的api
     */
    public static final String CONFIG_KEY_TMDB_API = "tmdb_api";
    /**
     * 配置键值：TMDB的api
     */
    public static final String CONFIG_KEY_IMDB_API = "imdb_api";
    /**
     * 配置键值：TMDB的api
     */
    public static final String CONFIG_KEY_TVDB_API = "tvdb_api";
    /**
     * 配置键值：TMDB的apiKey
     */
    public static final String CONFIG_KEY_TMDB_API_KEY = "tmdb_api_key";
    /**
     * 配置键值：通用剧集刮削
     */
    public static final String CONFIG_KEY_SCRAPE_TV = "scrape_tv_api";
    /**
     * 配置键值：通用电影刮削
     */
    public static final String CONFIG_KEY_SCRAPE_MOVIE = "scrape_movie_api";
    /**
     * 配置键值：TMDB的图片api
     */
    public static final String CONFIG_KEY_TMDB_IMAGE_API = "tmdb_image_api_key";

    /**
     * 配置键值：演员海报存储路径
     */
    public static final String CONFIG_KEY_PERFORMER_IMAGE_PATH = "performer_path";
    /**
     * 配置键值：扫描过滤目录
     */
    public static final String CONFIG_KEY_SCAN_FILTER_DIRECTORY = "scan_filter_directory";
    /**
     * 配置键值：扫描视频类型
     */
    public static final String CONFIG_KEY_SCAN_VIDEO_TYPE = "scan_video_type";

    /**
     * 配置键值：视频扫描定时
     */
    public static final String CONFIG_KEY_VIDEO_SCAN_CRON = "video_scan_cron";

    /**
     * TMDB图片下载类型：视频海报
     */
    public static final String TMDB_IMAGE_VIDEO_POSTER = "w500";
    /**
     * TMDB图片下载类型：视频背景
     */
    public static final String TMDB_IMAGE_VIDEO_BACKDROP = "w1920_and_h800_multi_faces";
    /**
     * TMDB图片下载类型：视频季海报
     */
    public static final String TMDB_IMAGE_VIDEO_SEASONS_POSTER = "w185";
    /**
     * TMDB图片下载类型：演员图片
     */
    public static final String TMDB_IMAGE_VIDEO_PERFORMER_POSTER = "w185";

    /**
     * 视频类型：剧集
     */
    public static final String VIDEO_TYPE_TV = "tv";
    /**
     * 视频类型：电影
     */
    public static final String VIDEO_TYPE_MOVIE = "movie";



    /**
     * 通用是标识
     */
    public static final String TRUE = "1";

    /**
     * 通用数据有效状态
     */
    public static final String VALID = "1";

    /**
     * 敏感信息加密盐值
     */
    public static String SALT = "58d37c8c6bcf4581f95e6b18c15d0a34";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * 分隔符
     */
    public static final String SPLIT_STR = ",";
}
