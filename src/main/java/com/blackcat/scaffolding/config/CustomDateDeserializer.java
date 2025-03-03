package com.blackcat.scaffolding.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义日期反序列化器
 * @author : zhangdahui  2024/12/3 下午6:10
 */
public class CustomDateDeserializer extends JsonDeserializer<LocalDateTime> {

    // 定义常规日期格式，进行匹配
    private static final Map<String, DateTimeFormatter> FORMATTERS = new HashMap<>();
    static {
        FORMATTERS.put("yyyy-MM-dd", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        FORMATTERS.put("yyyy-MM-dd HH:mm:ss", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        FORMATTERS.put("yyyy/MM/dd", DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        FORMATTERS.put("yyyy-MM-dd'T'HH:mm:ss.SSSX", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX"));
        FORMATTERS.put("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String dateString = p.getText();
        String pattern = "";
        for (Map.Entry<String, DateTimeFormatter> entry : FORMATTERS.entrySet()) {
            try {
                DateTimeFormatter formatter = entry.getValue();
                formatter.parse(dateString); // 尝试解析
                pattern = entry.getKey();      // 如果成功，返回格式
                System.out.println("pattern"+pattern);
            } catch (DateTimeParseException ignored) {
                // 忽略解析失败的格式
                System.out.println("解析失败"+entry.getKey());
            }
        }
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateString, inputFormatter);
    }
}
