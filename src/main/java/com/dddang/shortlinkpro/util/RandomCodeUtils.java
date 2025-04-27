package com.dddang.shortlinkpro.util;

import java.security.SecureRandom;
/**
 * @Description:
 * @Author : dddang
 * @Date :2025-04-25  下午2:53
 */
public class RandomCodeUtils {
    // 定义字符池（62个字符：字母+数字）
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateRandomCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }
}
