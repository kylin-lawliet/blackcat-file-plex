package com.blackcat.nas.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.blackcat.nas.common.result.AjaxResult;
import com.blackcat.nas.common.result.CustomPage;
import com.blackcat.nas.dao.*;
import com.blackcat.nas.entity.*;
import com.blackcat.nas.model.VideoScanFile;
import com.blackcat.nas.service.SysConfigService;
import com.blackcat.nas.service.VideoService;
import com.blackcat.nas.utils.HttpUtils;
import com.blackcat.nas.utils.ImageDownloader;
import com.blackcat.nas.utils.NfoParser;
import com.blackcat.nas.utils.VideoNameExtractor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.blackcat.nas.constant.Constant.*;

/**
 * @author : zhangdahui  2025/3/14 下午5:42
 */
@Service
public class VideoServiceImpl implements VideoService {

    private static final Logger log = LoggerFactory.getLogger(VideoServiceImpl.class);

    @Value("${server.servlet.context-path}")
    private String contextPath;
    @Autowired
    private FilePathMapper filePathMapper;
    @Autowired
    private VideoSeasonsMapper videoSeasonsMapper;
    @Autowired
    private PerformerMapper performerMapper;
    @Autowired
    private VideoPerformerMapper videoPerformerMapper;
    @Autowired
    private VideoEpisodesMapper episodesMapper;
    @Autowired
    private VideoScanMapper videoMapper;
    @Autowired
    private SysConfigService configService;

    @Override
    public AjaxResult getVideoDetail(String id, String showType) {
        VideoScan scan = videoMapper.selectById(id);
        // 所有季数据
        // 季度数据是否缺少
        // 相关剧场版
        // 演员信息

//        String api = configService.getConfigValue(CONFIG_KEY_TMDB_API);
//        String apikey = configService.getConfigValue(CONFIG_KEY_TMDB_API_KEY);
//        String utl = api  + showType + "/" + id ;
        return null;
    }

    @Override
    public AjaxResult scrapeAllMetadata() {
        List<VideoScan> list = videoMapper.selectList(null);
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(this::scrapeByTmdb);
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult scrapeMetadata(String id,String tmdbId) {
        VideoScan videoScan = videoMapper.selectById(id);
        if (StringUtils.isNotBlank(tmdbId)) {
            videoScan.setTmdbId(tmdbId);
        }
        System.out.println("tmdbId " +tmdbId);
        String scrape;
        if (VIDEO_TYPE_TV.equals(videoScan.getVideoType())) {
            scrape = configService.getConfigValue(CONFIG_KEY_SCRAPE_TV);
        }else {
            scrape = configService.getConfigValue(CONFIG_KEY_SCRAPE_MOVIE);
        }
        if(scrape.equals(CONFIG_KEY_TMDB_API)){
            if (StringUtils.isNotBlank(videoScan.getTmdbId())) {
                scrapeByTmdb(videoScan);
            }else {
                log.info("未找到TMDB ID根据影视名称查询API");
                queryTmdbByName(videoScan);
                if(StringUtils.isNotBlank(videoScan.getTmdbId())){
                    log.info("找到TMDB ID根据影视ID查询API");
                    scrapeByTmdb(videoScan);
                }
            }
        }
//        if(scrape.equals(CONFIG_KEY_IMDB_API)){
//            if (StringUtils.isNotBlank(videoScan.getImdbId())) {
//            }
//        }
//        if(scrape.equals(CONFIG_KEY_TVDB_API)){
//            if (StringUtils.isNotBlank(videoScan.getTvdbId())) {
//            }
//        }
        return AjaxResult.success();
    }

    private void scrapeByTmdb(VideoScan videoScan){
        String api = configService.getConfigValue(CONFIG_KEY_TMDB_API);
        String apikey = configService.getConfigValue(CONFIG_KEY_TMDB_API_KEY);
        String imageApi = configService.getConfigValue(CONFIG_KEY_TMDB_IMAGE_API);
        String url = api  + videoScan.getVideoType() + "/" + videoScan.getTmdbId() ;
        String rspStr;
        try {
            rspStr = HttpUtils.sendGetThrows(url, "api_key=" + apikey + "&language=zh-CN&append_to_response=credits", UTF8);
            JSONObject obj = JSONObject.parseObject(rspStr);
            VideoScan video = tmdbToVideoScan(obj, videoScan.getVideoType());
            videoScan.setVideoName(video.getVideoName());
            videoScan.setBroadcastTime(video.getBroadcastTime());
            videoScan.setIntroduction(video.getIntroduction());
            videoScan.setUserScore(video.getUserScore());
            videoScan.setSeriesName(video.getSeriesName());
            videoScan.setCollectionId(video.getCollectionId());
            videoScan.setOriginalTitle(video.getOriginalTitle());
            videoScan.setScrape(true);
            videoMapper.updateById(videoScan);
            File poster = new File(videoScan.getVideoPath() + "/poster.jpg");
            if (!poster.exists()) {
                ImageDownloader.downloadTmdbImage(imageApi,TMDB_IMAGE_VIDEO_POSTER,video.getProfilePath(),videoScan.getVideoPath(),"/poster.jpg");
            }
            File backdrop = new File(videoScan.getVideoPath() + "/backdrop.jpg");
            if (!backdrop.exists()) {
                ImageDownloader.downloadTmdbImage(imageApi,TMDB_IMAGE_VIDEO_BACKDROP,video.getBackdropPath(),videoScan.getVideoPath(),"/backdrop.jpg");
            }
            // 季数据
            if (CollectionUtils.isNotEmpty(video.getVideoSeasonsList())) {
                QueryWrapper<VideoSeasons> queryWrapper = new QueryWrapper<>();
                video.getVideoSeasonsList().forEach(i -> {
                    queryWrapper.clear();
                    queryWrapper.eq("video_id", videoScan.getVideoId());
                    queryWrapper.eq("seasons_number", i.getSeasonsNumber());
                    VideoSeasons videoSeasons = videoSeasonsMapper.selectOne(queryWrapper);
                    if (videoSeasons != null) {
                        videoSeasons.setTmdbId(videoScan.getTmdbId());
                        videoSeasons.setEpisodeCount(i.getEpisodeCount());
                        videoSeasons.setSeasonsName(i.getSeasonsName());
                        videoSeasons.setIntroduction(i.getIntroduction());
                        videoSeasons.setBroadcastTime(i.getBroadcastTime());
                        videoSeasonsMapper.updateById(videoSeasons);
                    }else {
                        i.setTmdbId(videoScan.getTmdbId());
                        i.setVideoId(videoScan.getVideoId());
                        videoSeasonsMapper.insert(i);
                        String fileName = "/season"+String.format("%02d", i.getSeasonsNumber())+"-poster.jpg";
                        ImageDownloader.downloadTmdbImage(imageApi,TMDB_IMAGE_VIDEO_SEASONS_POSTER,i.getPosterPath(),videoScan.getVideoPath(),fileName);
                    }
                    // 剧集信息
                    QueryWrapper<VideoEpisodes> episodesQueryWrapper = new QueryWrapper<>();
                    episodesQueryWrapper.eq("show_id", videoScan.getTmdbId());
                    episodesQueryWrapper.eq("season_number", i.getSeasonsNumber());
                    Long selectCount = episodesMapper.selectCount(episodesQueryWrapper);
                    if (selectCount == 0) {
                        queryTmdbEpisode(videoScan.getTmdbId(), i.getSeasonsNumber());
                    }
                });
            }
            if (CollectionUtils.isNotEmpty(video.getVideoPerformerList())) {
                String savePath = configService.getConfigValue(CONFIG_KEY_PERFORMER_IMAGE_PATH);
                // 删除历史
//                QueryWrapper<VideoPerformer> queryWrapperVideoPerformer = new QueryWrapper<>();
//                queryWrapperVideoPerformer.eq("video_id", videoScan.getVideoId());
//                videoPerformerMapper.delete(queryWrapperVideoPerformer);
                // 保存演员信息
                QueryWrapper<Performer> queryWrapperPerformer = new QueryWrapper<>();
                video.getVideoPerformerList().forEach(i -> {
                    queryWrapperPerformer.clear();
                    queryWrapperPerformer.eq("tmdb_id", i.getPerformerId());
                    Long count = performerMapper.selectCount(queryWrapperPerformer);
                    if (count == 0) {
                        Performer performer = new Performer();
                        performer.setName(i.getPerformerName());
                        performer.setTmdbId(i.getPerformerId());
                        performer.setOriginalName(i.getOriginalName());
                        ImageDownloader.downloadTmdbImage(imageApi,TMDB_IMAGE_VIDEO_PERFORMER_POSTER,i.getPosterPath(),savePath,i.getPerformerId()+".jpg");
                        performer.setProfilePath(savePath + i.getPerformerId() + ".jpg");
                        performerMapper.insert(performer);
                    }
                    QueryWrapper<VideoPerformer> queryWrapperVideoPerformer = new QueryWrapper<>();
                    queryWrapperVideoPerformer.eq("video_id", videoScan.getVideoId());
                    queryWrapperVideoPerformer.eq("performer_id", i.getPerformerId());
                    Long count1 = videoPerformerMapper.selectCount(queryWrapperVideoPerformer);
                    if (count1==0) {
                        i.setVideoId(videoScan.getVideoId());
                        videoPerformerMapper.insert(i);
                    }
                });
            }
        } catch (FileNotFoundException e) {
            log.warn("TMDB ID 不正确，未查询到对应数据：{}",videoScan.getTmdbId());
            log.warn("调用根据名称查询API重新查询数据，并修改数据");
            queryTmdbByName(videoScan);
            if(StringUtils.isNotBlank(videoScan.getTmdbId())){
                scrapeByTmdb(videoScan);
            }
        } catch (Exception e) {
            log.error("TMDB刮削数据异常", e);
        }

    }

    /**
     * 描述 :   查询剧集信息
     * @author : zhangdahui 2025/3/13 下午6:00
     * @param tmdbId ID
     * @param seasonNumber 季数
     */
    private void queryTmdbEpisode(String tmdbId,Integer seasonNumber) {
        String api = configService.getConfigValue(CONFIG_KEY_TMDB_API);
        String apikey = configService.getConfigValue(CONFIG_KEY_TMDB_API_KEY);
//        String api = "https://api.themoviedb.org/3/";
//        String apikey = "df0215f21f31be046230fcb5d69b22c9";
        String url = api + "tv/" + tmdbId + "/season/" + seasonNumber ;
        String rspStr = HttpUtils.sendGet(url, "api_key=" + apikey + "&language=zh-CN&append_to_response=credits", UTF8);
        JSONObject rsp = JSONObject.parseObject(rspStr);
        JSONArray result = rsp.getJSONArray("episodes");
        for (Object obj : result) {
            JSONObject jsonObject = (JSONObject) obj;
            VideoEpisodes episodes = new VideoEpisodes();
            episodes.setEpisodeNumber(jsonObject.getInteger("episode_number"));
            episodes.setName(jsonObject.getString("name"));
            episodes.setRuntime(jsonObject.getString("runtime"));
            episodes.setSeasonNumber(jsonObject.getInteger("season_number"));
            episodes.setBroadcastTime(jsonObject.getString("air_date"));
            episodes.setShowId(tmdbId);
            episodes.setIntroduction(jsonObject.getString("overview"));
//            System.out.println(episodes);
            episodesMapper.insert(episodes);
        }
    }

    /**
     * 描述 :   根据影片名称查询Tmdb
     * @author : zhangdahui 2025/3/13 下午5:58
     * @param videoScan  扫描数据
     */
    private void queryTmdbByName(VideoScan videoScan) {
        String api = configService.getConfigValue(CONFIG_KEY_TMDB_API);
        String apikey = configService.getConfigValue(CONFIG_KEY_TMDB_API_KEY);
        String url = api  +"search/"+ videoScan.getVideoType();
//        String query = "K 失落之王";
        String encodedQuery;
        try {
            encodedQuery = URLEncoder.encode(videoScan.getVideoName(), String.valueOf(StandardCharsets.UTF_8));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        String rspStr = HttpUtils.sendGet(url, "query=" + encodedQuery + "&api_key=" + apikey + "&language=zh-CN&append_to_response=credits", UTF8);
        JSONObject rsp = JSONObject.parseObject(rspStr);
        JSONArray result = rsp.getJSONArray("results");
        for (Object obj : result) {
            VideoScan video = tmdbToVideoScan(obj, videoScan.getVideoType());
            if (videoScan.getVideoName().equals(video.getVideoName())) {
                videoScan.setTmdbId(video.getTmdbId());
                videoScan.setVideoName(video.getVideoName());
                videoScan.setBroadcastTime(video.getBroadcastTime());
                videoScan.setIntroduction(video.getIntroduction());
                videoScan.setUserScore(video.getUserScore());
                videoMapper.updateById(videoScan);
            }
        }
    }

    /**
     * 描述 :   将TMDB查询结果转换成对象
     * @author : zhangdahui 2025/3/14 上午9:59
     * @param obj 查询结果
     * @param type  视频类型
     */
    private VideoScan tmdbToVideoScan(Object obj,String type){
        JSONObject jsonObject = (JSONObject) obj;
        VideoScan videoScan = new VideoScan();
        String name;
        String releaseDate;
        if (VIDEO_TYPE_TV.equals(type)) {
            name = jsonObject.getString("name");
            releaseDate = jsonObject.getString("first_air_date");
        }else {
            name = jsonObject.getString("title");
            releaseDate = jsonObject.getString("release_date");
            videoScan.setImdbId(jsonObject.getString("imdb_id"));
            // 系列
            if (jsonObject.getString("belongs_to_collection") != null) {
                JSONObject belongsToCollection = JSONObject.parseObject(jsonObject.getString("belongs_to_collection"));
                videoScan.setCollectionId(belongsToCollection.getString("id"));
                videoScan.setSeriesName(belongsToCollection.getString("name"));
            }
        }
        String id = jsonObject.getString("id");
        videoScan.setTmdbId(id);
        videoScan.setVideoName(name);
        videoScan.setBroadcastTime(releaseDate);
        videoScan.setOriginalTitle(jsonObject.getString("original_name"));
        videoScan.setIntroduction(jsonObject.getString("overview"));
        videoScan.setUserScore(jsonObject.getString("vote_average"));
        videoScan.setBackdropPath(jsonObject.getString("backdrop_path"));
        videoScan.setProfilePath(jsonObject.getString("poster_path"));
        // 分类
        JSONArray genresList = jsonObject.getJSONArray("genres");
        if (genresList != null) {
            StringJoiner joiner = new StringJoiner(",");
            for (Object object : genresList) {
                JSONObject genres = (JSONObject) object;
                joiner.add(genres.getString("name"));
            }
            videoScan.setVideoCategory(joiner.toString());
        }
        // 季
        JSONArray seasonsList = jsonObject.getJSONArray("seasons");
        if (seasonsList != null) {
            List<VideoSeasons> videoSeasonsList = getVideoSeasons(seasonsList);
            videoScan.setVideoSeasonsList(videoSeasonsList);
        }
        // 演员或CV
        JSONObject creditsObj = JSONObject.parseObject(jsonObject.getString("credits"));
        if (creditsObj != null) {
            JSONArray castList = creditsObj.getJSONArray("cast");
            if (castList != null) {
                List<VideoPerformer> videoPerformerList = getVideoPerformerList(castList, id);
                videoScan.setVideoPerformerList(videoPerformerList);
            }
        }
        return videoScan;
    }

    // 获取演员数据
    private List<VideoPerformer> getVideoPerformerList(JSONArray castList, String id) {
        List<VideoPerformer> videoPerformerList = new ArrayList<>();
        for (Object object : castList) {
            JSONObject cast = (JSONObject) object;
            VideoPerformer performer = new VideoPerformer();
            performer.setCharacterName(cast.getString("character"));
            performer.setPerformerId(cast.getString("id"));
            performer.setOrderNumber(cast.getInteger("order"));
            performer.setVideoId(Long.valueOf(id));
            performer.setPosterPath(cast.getString("profile_path"));
            performer.setPerformerName(cast.getString("name"));
            performer.setOriginalName(cast.getString("original_name"));
            videoPerformerList.add(performer);
        }
        return videoPerformerList;
    }

    // 获取季数据
    private List<VideoSeasons> getVideoSeasons(JSONArray seasonsList) {
        List<VideoSeasons> videoSeasonsList = new ArrayList<>();
        for (Object object : seasonsList) {
            JSONObject seasons = (JSONObject) object;
            VideoSeasons videoSeasons = new VideoSeasons();
            videoSeasons.setSeasonsName(seasons.getString("name"));
            videoSeasons.setSeasonsNumber(seasons.getInteger("season_number"));
            videoSeasons.setEpisodeCount(seasons.getInteger("episode_count"));
            videoSeasons.setIntroduction(seasons.getString("overview"));
            videoSeasons.setBroadcastTime(seasons.getString("air_date"));
            videoSeasons.setPosterPath(seasons.getString("poster_path"));
            videoSeasonsList.add(videoSeasons);
        }
        return videoSeasonsList;
    }

    @Override
    public AjaxResult scanVideo() {
        // 获取需要扫描的地址
        QueryWrapper<FilePath> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("file_type", "1","2");
        List<FilePath> filePathList = filePathMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(filePathList)) {
            String scanFilterDirectory = configService.getConfigValue(CONFIG_KEY_SCAN_FILTER_DIRECTORY);
            String scanVideoType = configService.getConfigValue(CONFIG_KEY_SCAN_VIDEO_TYPE);
            filePathList.forEach(i -> scanVideoByPath(i.getFilePath(), i.getFileType() == 1 ?VIDEO_TYPE_MOVIE : VIDEO_TYPE_TV,scanFilterDirectory,scanVideoType));
        }
        return AjaxResult.success();
    }

    /**
     * 描述 :   获取分类目录
     * @author : zhangdahui 2025/3/11 上午10:31
     * @param path  文件目录
     */
    public static String extractMiddleValueWithPath(String path) {
        java.nio.file.Path filePath = Paths.get(path);
        int nameCount = filePath.getNameCount();
        if (nameCount >= 2) {
            return filePath.getName(nameCount - 2).toString();
        }
        return null;
    }

    @Override
    public AjaxResult scanVideoByPath(String filePath, String type, String scanFilterDirectory, String scanVideoType) {
        // 查询过滤配置
        if(StringUtils.isBlank(scanFilterDirectory)){
            scanFilterDirectory = configService.getConfigValue(CONFIG_KEY_SCAN_FILTER_DIRECTORY);
            scanVideoType = configService.getConfigValue(CONFIG_KEY_SCAN_VIDEO_TYPE);
        }
        List<String> scanFilterDirectoryList = Arrays.asList(scanFilterDirectory.split(SPLIT_STR));
        List<String> scanVideoTypeList = Arrays.asList(scanVideoType.split(SPLIT_STR));
        // 开始扫描
        List<VideoScanFile> fileList = new ArrayList<>(16);
        // 扫描目录
        List<String> scanResultList = scanVideoFiles(filePath,scanFilterDirectoryList,scanVideoTypeList);
        for (String scanResult : scanResultList) {
            VideoScanFile file= VideoNameExtractor.extractVideoName(scanResult);
            if (file != null) {
                if(fileList.stream().noneMatch(str -> str.getFileName().equals(file.getFileName()))){
                    fileList.add(file);
                    QueryWrapper<VideoScan> scanQueryWrapper = new QueryWrapper<>();
                    scanQueryWrapper.eq("video_name", file.getFileName());
                    VideoScan videoScan = videoMapper.selectOne(scanQueryWrapper);
                    if (videoScan==null) {
                        // 查找本地已刮削的元数据信息
                        String nfo = getNfo(file.getVideoPath());
                        if (StringUtils.isNotBlank(nfo)) {
                            VideoScan scan = NfoParser.xml(nfo);
                            scan.setVideoPath(file.getVideoPath());
                            scan.setVideoName(file.getFileName());
                            scan.setVideoType(type);
                            scan.setSeriesName(file.getFileName());
                            scan.setVideoDirectory(extractMiddleValueWithPath(file.getVideoPath()));
                            videoMapper.insert(scan);
                            // 查找季数据
                            if(VIDEO_TYPE_TV.equals(type)){
                                File videoPath = new File(file.getVideoPath());
                                File[] files = videoPath.listFiles();
                                if (files!=null) {
                                    for (File seasons : files) {
                                        if (seasons.isDirectory() && (seasons.getName().matches("S\\d+") || seasons.getName().matches("第.*季"))) {
                                            VideoSeasons videoSeasons = new VideoSeasons();
                                            videoSeasons.setFilePath(seasons.getAbsolutePath());
                                            videoSeasons.setSeriesName(scan.getSeriesName());
                                            videoSeasons.setTmdbId(scan.getTmdbId());
                                            videoSeasons.setSeasonsNumber(extractSeasonNumber(seasons.getName()));
                                            videoSeasons.setVideoId(scan.getVideoId());
                                            videoSeasonsMapper.insert(videoSeasons);
                                        }
                                    }
                                }
                            }
                        }
                    }else{
                        // 如果数据库存在同名，就更新视频路径及视频分类目录即可
                        videoScan.setVideoDirectory(extractMiddleValueWithPath(file.getVideoPath()));
                        videoScan.setVideoPath(file.getVideoPath());
                        videoMapper.updateById(videoScan);
                    }
                }
            }
        }
        return AjaxResult.success();
    }

    /**
     * 描述 :   从目录名中截取季数
     * @author : zhangdahui 2025/3/13 下午4:04
     * @param input 目录名
     */
    public static Integer extractSeasonNumber(String input) {
        // 定义正则表达式模式，用于匹配数字部分
        String regex = "[S第](\\d+)季?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            // 提取匹配到的数字部分并转换为整数
            return Integer.parseInt(matcher.group(1));
        }
        return null;
    }

    /**
     * 描述 :   解析本地Nfo文件，获取视频信息
     * @author : zhangdahui 2025/3/11 上午10:27
     * @param directoryPath  视频根目录
     */
    private static String getNfo(String directoryPath){
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            // 定义一个 FilenameFilter 来筛选 .nfo 文件
            FilenameFilter filter = (dir, name) -> name.endsWith(".nfo");
            // 获取符合条件的文件
            File[] nfoFiles = directory.listFiles(filter);
            if (nfoFiles != null) {
                for (File nfoFile : nfoFiles) {
                    if (!nfoFile.getName().contains("._")) {
                        return nfoFile.getAbsolutePath();
                    }
                }
            }
            return null;
        } else {
            System.out.println("指定的目录不存在或不是一个有效的目录。");
            return null;
        }
    }

    /**
     * 递归扫描目录，返回所有视频文件路径
     */
    public static List<String> scanVideoFiles(String scanPath,List<String> scanFilterDirectoryList,List<String> scanVideoTypeList) {
        List<String> videoFiles = new ArrayList<>();
        File rootDir = new File(scanPath);
        if (rootDir.exists() && rootDir.isDirectory()) {
            scanDirectory(rootDir, videoFiles,scanFilterDirectoryList,scanVideoTypeList);
        }
        return videoFiles;
    }

    /**
     * 递归扫描目录
     */
    private static void scanDirectory(File directory, List<String> videoFiles,List<String> scanFilterDirectoryList,List<String> scanVideoTypeList) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory() && !scanFilterDirectoryList.contains(file.getName())) {
                    // 如果是目录，递归扫描
                    scanDirectory(file, videoFiles, scanFilterDirectoryList, scanVideoTypeList);
                } else if (isVideoFile(file,scanVideoTypeList)) {
                    // 如果是视频文件，添加到结果列表
                    videoFiles.add(file.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 检查文件是否为视频文件
     */
    private static boolean isVideoFile(File file,List<String> scanVideoTypeList) {
        String fileName = file.getName().toLowerCase();
        for (String extension : scanVideoTypeList) {
            if (fileName.endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public AjaxResult selectLibraryPage(Integer pageNow, Integer pageSize,String name,String category) {
        String merge = configService.getConfigValue(CONFIG_KEY_MERGE);
        if (TRUE.equals(merge)){
            int count = videoMapper.selectPageBySeriesNameCount(name,category);
            if (count == 0) {
                return AjaxResult.success(new CustomPage());
            }
            List<VideoScan> list = videoMapper.selectPageBySeriesName((pageNow - 1) * pageSize, pageSize, name,category);
            setHttpUrl(list);
            return AjaxResult.success(new CustomPage(list,count,pageNow,pageSize));
        }else {
            Page<VideoScan> page = new Page<>(pageNow, pageSize);
            QueryWrapper<VideoScan> queryWrapper = new QueryWrapper<>();
            if (StringUtils.isNotBlank(name)) {
                queryWrapper.like("video_name", name);
            }
            if (StringUtils.isNotBlank(category)) {
                queryWrapper.like("video_directory", category);
            }
            queryWrapper.orderByAsc("video_name");
            Page<VideoScan> result = videoMapper.selectPage(page, queryWrapper);
            result.setRecords(setHttpUrl(result.getRecords()));
            return AjaxResult.success(CustomPage.mybatisPage(result));
        }
    }

    private List<VideoScan> setHttpUrl(List<VideoScan> list){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (CollectionUtils.isNotEmpty(list)) {
            list.forEach(i -> {
                i.setVideoName(i.getSeriesName());
                String ip ;
                try {
                    ip = InetAddress.getLocalHost().getHostAddress();
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
                int port = request.getLocalPort();
                i.setHttpUrl("http://" + ip + ":" + port + contextPath +"/files/getImg?fileName=poster.jpg&path=" + i.getVideoPath());
            });
        }
        return list;
    }
}
