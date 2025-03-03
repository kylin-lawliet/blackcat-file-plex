package com.blackcat.scaffolding.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * 日期全局转换（使用 Jackson 配置）
 * @author : zhangdahui  2024/12/3 下午6:09
 */
@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        // 自定义 LocalDateTime 格式化器
        javaTimeModule.addDeserializer(LocalDateTime.class, new CustomDateDeserializer());
        javaTimeModule.addSerializer(LocalDateTime.class, new CustomDateSerializer());

        mapper.registerModule(javaTimeModule);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}
