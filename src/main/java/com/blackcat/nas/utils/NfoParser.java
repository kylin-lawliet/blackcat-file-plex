package com.blackcat.nas.utils;
import com.blackcat.nas.entity.VideoScan;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

import static com.blackcat.nas.utils.VideoNameExtractor.extractVideoName;

/**
 * nfo文件解析
 * @author : zhangdahui  2025/3/9 下午12:40
 */
public class NfoParser {

    private static final Logger log = LoggerFactory.getLogger(NfoParser.class);

    public static VideoScan xml(String path){
        VideoScan scan = new VideoScan();
        try {
            // 创建 DocumentBuilderFactory 实例
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // 创建 DocumentBuilder 实例
            DocumentBuilder builder = factory.newDocumentBuilder();
//            System.out.println("nfoFile :"+path);
            // 解析本地 .nfo 文件
            File nfoFile = new File(path);
            Document doc = builder.parse(nfoFile);
            // 规范化文档
            doc.getDocumentElement().normalize();
            // 获取根元素
            Element root = doc.getDocumentElement();
            // 假设 .nfo 文件中有 <title> 和 <year> 标签
//            NodeList titleList = root.getElementsByTagName("title");
            NodeList originalTitleList = root.getElementsByTagName("originaltitle");
            NodeList yearList = root.getElementsByTagName("year");
            NodeList releaseDateList = root.getElementsByTagName("releasedate");
            NodeList ratingList = root.getElementsByTagName("rating");
            NodeList plotList  = root.getElementsByTagName("plot");
            NodeList runtimeList  = root.getElementsByTagName("runtime");
            NodeList genreList  = root.getElementsByTagName("genre");
            String originalTitle = getTextContent(originalTitleList);
            scan.setOriginalTitle(originalTitle);
            String year = getTextContent(yearList);
            scan.setYear(year);
            String plot = getTextContent(plotList);
            scan.setIntroduction(plot);
            String releaseDate = getTextContent(releaseDateList);
            scan.setBroadcastTime(releaseDate);
            String rating = getTextContent(ratingList);
            scan.setUserScore(rating);
            // 提取 <uniqueid> 标签中的值
            String tmdbUniqueId = "";
            NodeList uniqueIdList = root.getElementsByTagName("uniqueid");
            for (int i = 0; i < uniqueIdList.getLength(); i++) {
                Node uniqueIdNode = uniqueIdList.item(i);
                if (uniqueIdNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element uniqueIdElement = (Element) uniqueIdNode;
                    String type = uniqueIdElement.getAttribute("type");
                    if ("tmdb".equals(type)) {
                        tmdbUniqueId = uniqueIdElement.getTextContent();
                        scan.setTmdbId(tmdbUniqueId);
                    }
                    if ("imdb".equals(type)) {
                        tmdbUniqueId = uniqueIdElement.getTextContent();
                        scan.setImdbId(tmdbUniqueId);
                    }
                    if ("tvdb".equals(type)) {
                        tmdbUniqueId = uniqueIdElement.getTextContent();
                        scan.setTvdbId(tmdbUniqueId);
                    }
                }
            }
            String runtime = getTextContent(runtimeList);
            scan.setRunTime(runtime);
            StringJoiner genre = new StringJoiner(",");
            if (genreList.getLength() > 0) {
                for (int i = 0; i <genreList.getLength(); i++) {
                    Node genreNode = genreList.item(i);
                    if (genreNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element genreElement = (Element) genreNode;
                        genre.add(genreElement.getTextContent());
//                        System.out.println("genreElement.getTextContent() "+genreElement.getTextContent());
                    }
                }
            }
            scan.setVideoCategory(genre.toString());
//            System.out.println("scan.getVideoCategory() "+scan.getVideoCategory());
        } catch (Exception e) {
            log.error("解析nfo文件错误",e);
        }
        return scan;
    }

    private static String getTextContent(NodeList nodeList){
        Node node = nodeList.item(0);
        if (node != null && node.getNodeType() == Node.ELEMENT_NODE) {
            Element nodeElement = (Element) node;
//            System.out.println("nodeElement.getTextContent() "+nodeElement.getTextContent());
            return nodeElement.getTextContent();
        }
        return "";
    }

    public static VideoScan ini(String path){
        Map<String, String> infoMap = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 去除首尾空格
                line = line.trim();
                if (line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    infoMap.put(key, value);
                }
            }
            // 输出解析结果
            for (Map.Entry<String, String> entry : infoMap.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        VideoScan scan = new VideoScan();
        return scan;
    }


    public static void main(String[] args) {
//        String path = "/Users/blackcat/Documents/file-plex/movie/国语电影/东北警察故事2(2023)/东北警察故事2.2023.2160p.nfo";
//        xml(path);

        String path = "/Users/blackcat/Documents/file-plex/anime/日番/宝石之国(2017)/tvshow.nfo";
        xml(path);
    }
}
