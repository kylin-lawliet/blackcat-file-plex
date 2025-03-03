package com.blackcat.scaffolding.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.blackcat.scaffolding.constant.Constant.SALT;

/**
 * Jasypt 的加密工具类
 * @author : zhangdahui  2024/8/23 下午1:31
 */
@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Bean
    public BasicTextEncryptor textEncryptor() {
        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
        textEncryptor.setPassword(SALT);
        return textEncryptor;
    }
}
