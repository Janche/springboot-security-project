package com.example.janche.common.utils.encrypt;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;


/**
 * <p>支持HMAC-SHA1消息签名 及 DES/AES对称加密的工具类.</p>
 * <li>支持Hex与Base64两种编码方式.</li>
 * @author qxb
 * @version 1.0
 * @since 2018.01.24
 *
 */
public class Cryptos {
    private static Logger logger = LoggerFactory.getLogger(Cryptos.class);
    private Cryptos() {
    }

    /** Base64 编码 */
    private static final Base64 B64 = new Base64();
    /** 安全的随机数源 */
    private static final SecureRandom RANDOM = new SecureRandom();
    /** SHA-1加密 */
    private static MessageDigest SHA_1 = null;

    static {
        init();
    }


    /**
     * 初始化
     */
    private static void init() {
        try {
            SHA_1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * SHA-1加密
     *
     * @param str 明文
     * @return    密文
     */
    public static String sha1(String str) {
        return new String(B64.encode(SHA_1.digest(str.getBytes())));
    }

    /**
     * SHA-1加密(Url安全)
     *
     * @param str 明文
     * @return 密文
     */
    public static String sha1Url(String str) {
        return new String(Base64.encodeBase64URLSafeString(SHA_1.digest(str.getBytes())));
    }

    /**
     * Base64编码
     *
     * @param bs byte数组
     * @return 编码后的byte数组
     */
    public static byte[] b64Encode(byte[] bs) {
        return B64.encode(bs);
    }

    /**
     * Base64编码字符串
     *
     * @param str 需要编码的字符串
     * @return 编码后的字符串
     */
    public static String b64Encode(String str) {
        if (null != str) {
            return new String(B64.encode(str.getBytes()));
        }
        return null;
    }

    /**
     * Base64编码字符串(Url安全)
     *
     * @param str 需要编码的字符串
     * @return 编码后的字符串
     */
    public static String b64Url(String str) {
        if (null != str) {
            return Base64.encodeBase64URLSafeString(str.getBytes());
        }
        return null;
    }

    /**
     * Base64解码
     *
     * @param bs byte数组
     * @return 解码后的byte数组
     */
    public static byte[] b64Decode(byte[] bs) {
        return B64.decode(bs);
    }

    /**
     * Base64解码字符串
     *
     * @param str 需要解码的字符串
     * @return 解码后的字符串
     */
    public static String b64Decode(String str) {
        if (null != str) {
            byte[] bs = B64.decode(str.getBytes());
            if (null != bs) {
                return new String(bs);
            }
        }
        return null;
    }

    /**
     * 生成32位MD5密文
     *
     * @param str 明文
     * @return 密文
     */
    public static String md5(String str) {
        if (null != str) {
            return DigestUtils.md5Hex(str);
        }
        return null;
    }

    /** DES加密算法 */
    private static final String DES_ALGORITHM = "DESede"; // 可用 DES,DESede,Blowfish
    /** DES默认加密 */
    private static Cipher DES_CIPHER_ENC = null;
    /** DES默认解密 */
    private static Cipher DES_CIPHER_DEC = null;

    static {
        // 添加JCE算法
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        // 初始化默认DES加密
        try {
            // 密钥
            SecretKey desKey = new SecretKeySpec(new byte[] { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10, 0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD, 0x55, 0x66, 0x77, 0x29, 0x74,
                    (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2 }, DES_ALGORITHM);
            // 初始化默认加密
            DES_CIPHER_ENC = Cipher.getInstance(DES_ALGORITHM);
            DES_CIPHER_ENC.init(Cipher.ENCRYPT_MODE, desKey, RANDOM);
            // 初始化默认解密
            DES_CIPHER_DEC = Cipher.getInstance(DES_ALGORITHM);
            DES_CIPHER_DEC.init(Cipher.DECRYPT_MODE, desKey, RANDOM);
        } catch (Exception e) {
            logger.error("DES默认加密解密初始化失败：" + e.getMessage(), e);
            throw new RuntimeException("DES默认加密解密初始化失败：" + e.getMessage(), e);
        }
    }

    /**
     * DES加密(默认密钥)
     *
     * @param str 需要加密的明文
     * @return 加密后的密文(base64编码字符串)
     */
    public static String desEncryp(String str) {
        return desEncryp(str, false);
    }

    /**
     * DES加密(默认密钥) 是否需要URL安全
     *
     * @param str 需要加密的明文
     * @return 加密后的密文(base64编码字符串,Url安全)
     */
    public static String desEncrypUrl(String str) {
        return desEncryp(str, true);
    }

    /**
     * DES加密(默认密钥)
     *
     * @param str 需要加密的明文
     * @param urlSafety 密文是否需要Url安全
     * @return 加密后的密文(str为null返回null)
     */
    public static String desEncryp(String str, boolean urlSafety) {
        if (null != str) {
            try {

                byte[] bytes = DES_CIPHER_ENC.doFinal(str.getBytes("UTF-8"));// 加密
                if (urlSafety) {
                    return Base64.encodeBase64URLSafeString(bytes);
                } else {
                    return new String(B64.encode(bytes));
                }
            } catch (Exception e) {
                logger.error("DES加密失败, 密文：" + str + ", 错误：" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * DES解密(默认密钥)
     *
     * @param str 需要解密的密文(base64编码字符串)
     * @return 解密后的明文
     */
    public static String desDecrypt(String str) {
        if (null != str) {
            try {
                byte[] bytes = DES_CIPHER_DEC.doFinal(B64.decode(str));// 解密
                return new String(bytes, "UTF-8");
            } catch (Exception e) {
                logger.error("DES解密失败, 密文：" + str + ", 错误：" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * DES加密
     *
     * @param str 需要加密的明文
     * @param key 密钥(长度小于24字节自动补足，大于24取前24字节)
     * @return 加密后的密文(base64编码字符串)
     */
    public static String desEncryp(String str, String key) {
        return desEncryp(str, key, false);
    }

    /**
     * DES加密
     *
     * @param str 需要加密的明文
     * @param key 密钥(长度小于24字节自动补足，大于24取前24字节)
     * @return 加密后的密文(base64编码字符串,Url安全)
     */
    public static String desEncrypUrl(String str, String key) {
        return desEncryp(str, key, true);
    }

    /**
     * DES加密
     *
     * @param str 需要加密的明文
     * @param key 密钥(长度小于24字节自动补足，大于24取前24字节)
     * @param urlSafety 密文是否需要Url安全
     * @return 加密后的密文(str/key为null返回null)
     */
    public static String desEncryp(String str, String key, boolean urlSafety) {
        if (null != str && null != key) {
            try {
                Cipher c = Cipher.getInstance(DES_ALGORITHM);
                c.init(Cipher.ENCRYPT_MODE, desKey(key), RANDOM);
                // 加密
                byte[] bytes = c.doFinal(str.getBytes("UTF-8"));// 加密
                // 返回b64处理后的字符串
                if (urlSafety) {
                    return Base64.encodeBase64URLSafeString(bytes);
                } else {
                    return new String(B64.encode(bytes));
                }
            } catch (Exception e) {
                logger.error("DES加密失败, 密文：" + str + ", key：" + key + ", 错误：" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * DES解密
     *
     * @param str 需要解密的密文(base64编码字符串)
     * @param key 密钥(长度小于24字节自动补足，大于24取前24字节)
     * @return 解密后的明文
     */
    public static String desDecrypt(String str, String key) {
        if (null != str && null != key) {
            try {
                Cipher c = Cipher.getInstance(DES_ALGORITHM);
                c.init(Cipher.DECRYPT_MODE, desKey(key), RANDOM);
                byte[] bytes = c.doFinal(B64.decode(str));
                return new String(bytes, "UTF-8");
            } catch (BadPaddingException e) {
                logger.error("DES解密失败, 密文：" + str + ", key：" + key + ", 错误：" + e.getMessage());
            } catch (Exception e) {
                logger.error("DES解密失败, 密文：" + str + ", key：" + key + ", 错误：" + e.getMessage());
            }
        }
        return null;
    }


    /**
     * DES密钥
     * @param key
     * @return
     */
    private static SecretKey desKey(String key) {
        byte[] bs = key.getBytes();
        if (bs.length != 24) {
            bs = Arrays.copyOf(bs, 24);// 处理数组长度为24
        }
        return new SecretKeySpec(bs, DES_ALGORITHM);
    }

    /** AES加密算法 */
    private static final String AES_ALGORITHM = "AES";

    /**
     * AES加密
     *
     * @param str 需要加密的明文
     * @param key 密钥
     * @return 加密后的密文(str/key为null返回null)
     */
    public static String aesEncryp(String str, String key) {
        return aesEncryp(str, key, false);
    }

    /**
     * AES加密
     *
     * @param str 需要加密的明文
     * @param key 密钥
     * @param urlSafety 密文是否需要Url安全
     * @return 加密后的密文(str/key为null返回null)
     */
    public static String aesEncryp(String str, String key, boolean urlSafety) {
        if (null != str && null != key) {
            try {
                Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                c.init(Cipher.ENCRYPT_MODE, aesKey(key), RANDOM);
                byte[] bytes = c.doFinal(str.getBytes("UTF-8"));// 加密
                if (urlSafety) {
                    return Base64.encodeBase64URLSafeString(bytes);
                } else {
                    return new String(B64.encode(bytes));
                }
            } catch (Exception e) {
                logger.error("AES加密失败, 密文：" + str + ", key：" + key + ", 错误：" + e.getMessage());
            }
        }
        return null;
    }

    /**
     * AES解密
     *
     * @param str 需要解密的密文(base64编码字符串)
     * @param key 密钥
     * @return 解密后的明文
     */
    public static String aesDecrypt(String str, String key) {
        if (null != str && null != key) {
            try {
                Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
                c.init(Cipher.DECRYPT_MODE, aesKey(key), RANDOM);
                return new String(c.doFinal(B64.decode(str)),"UTF-8");// 解密
            } catch (BadPaddingException e) {
                logger.error("AES解密失败, 密文：" + str + ", key：" + key + ", 错误：" + e.getMessage());
            } catch (Exception e) {
                logger.error("AES解密失败, 密文：" + str + ", key：" + key + ", 错误：" + e.getMessage());
            }
        }
        return null;
    }


    /**
     * AES密钥
     * @param key
     * @return
     */
    private static SecretKeySpec aesKey(String key) {
        byte[] bs = key.getBytes();
        if (bs.length != 16) {
            bs = Arrays.copyOf(bs, 16);// 处理数组长度为16
        }
        return new SecretKeySpec(bs, AES_ALGORITHM);
    }

    public static void main(String[] args){
        StringBuffer s = new StringBuffer();
        StringBuffer s1 = new StringBuffer();
        for(int i=0;i<10;i++) {
            s.append(""+i);
            s1.append(",'"+i+"'");
        }
        System.out.println(s.toString());
        System.out.println(s1.toString());
    }
}

