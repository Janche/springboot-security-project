package com.example.janche.common.utils.encrypt;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * <p>      </p>
 *
 * @author qxb
 * @version v1.0
 * @since 2018/9/20 17:33
 */
public class TestDecryptCode {
    public static byte[] decryptByPublicKey(byte[] data,byte[] key) throws Exception{

        //实例化密钥工厂
        KeyFactory keyFactory=KeyFactory.getInstance("RSA");
        //初始化公钥
        //密钥材料转换
        X509EncodedKeySpec x509KeySpec=new X509EncodedKeySpec(key);
        //产生公钥
        PublicKey pubKey=keyFactory.generatePublic(x509KeySpec);
        //数据解密
        Cipher cipher=Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, pubKey);
        return cipher.doFinal(data);
    }
    public static void main(String[] args) throws Exception {
        String publickey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDj+78kFLF8w1vjX1VXafTFJlGZ5tQ05jvNIFODi1sKWDQtGI+J5mRBNfIlvZgY4Evq1kZpUe20aeU1pzFZCnvLha3KFsB5dlCSPs4JJ9UUsN2FoEGv6Iajuv+lOVMhFUh2pX64qsJgvLHD/S9i+nD+FVvrEgJpkww1CECJKu26NwIDAQAB";
        byte[] key = Base64Utils.decodeFromString(publickey);
        String data ="NL5b6ytxzKRlJwu5gfIvYSTxEU0Icm55Ymk5Hy3DHaO/3ALlsrumsQohmB5L88fP/HL3+nbTs3VRysEw7+7qDgvoDzlOSizgR8Mp3M1o846QrkEVQszPD4aSN4lZ7jDrH/lenaPiz7SDET7PbaTZCFlg0Y64NE5cmH+BmoYLca8=";
        byte[] value = Base64Utils.decodeFromString(data);
        byte[] decode1=TestDecryptCode.decryptByPublicKey(value, key);
        System.out.println(new String(decode1));
    }
}
