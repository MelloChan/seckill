package com.mello.utils;

/**
 * 加密工具类
 * Created by MelloChan on 2017/10/10.
 */
public class DigestUtils {
    private DigestUtils(){}
    public static String getMD5(String string){
        return org.springframework.util.DigestUtils.md5DigestAsHex(string.getBytes());
    }
}
