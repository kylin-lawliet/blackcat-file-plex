package com.blackcat.scaffolding.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;

import static com.blackcat.scaffolding.constant.Constant.SALT;

/**
 * SM4加密解密
 * @author : zhangdahui  2024/8/23 下午1:43
 */
public class Sm4Util {
    public static final String PROVIDER_KEY = "BC";
    public static final String ALGORITHM_NAME = "SM4";
    public static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";

    static{
        if (Security.getProvider(PROVIDER_KEY) == null){
            Security.addProvider(new BouncyCastleProvider());
        }
    }

    /**
     * 描述 :  加密
     * @author : zhangdahui 2024/6/24 下午5:41
     * @param plainText 需要加密的明文
     */
    public static String encrypt(String plainText) throws Exception {
        byte[] keyByte = ByteUtils.fromHexString(SALT);
        byte[] plainData = plainText.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME_ECB_PADDING, PROVIDER_KEY);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyByte, ALGORITHM_NAME);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedData = cipher.doFinal(plainData);
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    /**
     * 描述 :  解密
     * @author : zhangdahui 2024/6/24 下午5:41
     * @param cipherText 需要解密的密文
     */
    public static String decrypt(String cipherText) throws Exception {
        byte[] keyByte = ByteUtils.fromHexString(SALT);
        byte[] encryptedData = Base64.getDecoder().decode(cipherText);
        Cipher cipher = Cipher.getInstance(ALGORITHM_NAME_ECB_PADDING, PROVIDER_KEY);
        SecretKeySpec secretKeySpec = new SecretKeySpec(keyByte, ALGORITHM_NAME);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec );
        byte[] decryptedData = cipher.doFinal(encryptedData);
        return new String(decryptedData, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) throws Exception {
        String password = "123456";
        System.out.println(encrypt(password));
        System.out.println(decrypt(encrypt(password)));
    }

}
