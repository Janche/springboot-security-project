package com.example.janche.common.utils;

import java.security.MessageDigest;

/**
 * @author lirong
 * @ClassName: MD5Util
 * @Description: MD5加密解密工具类
 * @date 2018-12-14 10:18
 */

public class MD5Util {
    private static final String SALT = "CDJCY";

    /**
     * 使用系统指定的盐加密
     * @param password
     * @return
     */
    public static String encode(String password) {
        return MD5Util.encode(SALT, password);
    }

    /**
     * 使用传入的盐加密
     * @param salt
     * @param password
     * @return
     */
    public static String encode(String salt, String password) {
        password = password + salt;
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        char[] charArray = password.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }

            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    public static void main(String[] args) {
        System.out.println(MD5Util.encode("123456"));
    }
}
