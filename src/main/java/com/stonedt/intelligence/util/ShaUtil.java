package com.stonedt.intelligence.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @description: SHA工具类
 * @author 文轩
 **/
public class ShaUtil {

    private static final String SHA_1 = "SHA-1";
    private static final String SHA_224 = "SHA-224";
    private static final String SHA_256 = "SHA-256";
    private static final String SHA_384 = "SHA-384";
    private static final String SHA_512 = "SHA-512";

    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getSHA1(String painText, boolean uppercase) {
        return getSha(painText, SHA_1, uppercase);
    }

    public static String getSHA224(String painText, boolean uppercase) {
        return getSha(painText, SHA_224, uppercase);
    }

    public static String getSHA256(String painText, boolean uppercase) {
        return getSha(painText, SHA_256, uppercase);
    }

    public static String getSHA384(String painText, boolean uppercase) {
        return getSha(painText, SHA_384, uppercase);
    }

    public static String getSHA512(String painText, boolean uppercase) {
        return getSha(painText, SHA_512, uppercase);
    }

    /**
     * 利用Java原生摘要实现SHA加密(支持大小写，默认小写)
     *
     * @param plainText 要加密的数据
     * @param algorithm 要使用的算法（SHA-1,SHA-2224,SHA-256,SHA-384,SHA-512）
     * @param uppercase 是否转为大写
     * @return
     */
    private static String getSha(String plainText, String algorithm, boolean uppercase) {
        //输入的字符串转换成字节数组
        byte[] bytes = plainText.getBytes(StandardCharsets.UTF_8);
        MessageDigest messageDigest;
        try {
            //获得SHA转换器
            messageDigest = MessageDigest.getInstance(algorithm);
            //bytes是输入字符串转换得到的字节数组
            messageDigest.update(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA签名过程中出现错误,算法异常");
        }
        //转换并返回结果，也是字节数组，包含16个元素
        byte[] digest = messageDigest.digest();
        //字符数组转换成字符串返回
        String result = byteArrayToHexString(digest);
        //转换大写
        return uppercase ? result.toUpperCase() : result;
    }

    /**
     * 将字节数组转为16进制字符串
     *
     * @param bytes 要转换的字节数组
     * @return
     */
    private static String byteArrayToHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            //java.lang.Integer.toHexString() 方法的参数是int(32位)类型，
            //如果输入一个byte(8位)类型的数字，这个方法会把这个数字的高24为也看作有效位，就会出现错误
            //如果使用& 0XFF操作，可以把高24位置0以避免这样错误
            String temp = Integer.toHexString(b & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                builder.append("0");
            }
            builder.append(temp);
        }
        return builder.toString();
    }

}

