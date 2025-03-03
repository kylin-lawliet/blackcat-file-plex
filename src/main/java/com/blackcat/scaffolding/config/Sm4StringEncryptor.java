package com.blackcat.scaffolding.config;

import com.blackcat.scaffolding.utils.Sm4Util;
import org.jasypt.encryption.StringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.blackcat.scaffolding.utils.StringUtils;

/**
 * 自定义的加密工具
 * @author : zhangdahui  2024/8/23 下午1:42
 */
@Component
public class Sm4StringEncryptor implements StringEncryptor {
    private static final Logger logger = LoggerFactory.getLogger(Sm4StringEncryptor.class);

    @Override
    public String encrypt(String plainText) {
        if (StringUtils.isNotEmpty(plainText)){
            try {
                plainText = Sm4Util.encrypt(plainText);
            } catch (Exception e) {
                logger.error("SM4加密异常: ", e);
            }
        }
        return plainText;
    }

    @Override
    public String decrypt(String cipherText) {
        if (StringUtils.isNotEmpty(cipherText)){
            try {
                cipherText = Sm4Util.decrypt(cipherText);
            } catch (Exception e) {
                logger.error("SM4解密异常: ", e);
            }
        }
        return cipherText;
    }
}