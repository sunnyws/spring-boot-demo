package com.sunnyws.dict.constant;

/**
 * - @Description:  RedisConfig
 * - @Author qinlang
 * - @Date 2020/7/14 14:19
 */


public interface CommonConstant {

    public static final Integer SC_OK_200 = 200;

    public static final Integer SC_INTERNAL_SERVER_ERROR_500 = 500;

    public static final Integer SC_NO_AUTHZ=510;

    /**
     * 字典缓存key
     */
    public static final String DICT_CACHE = "cache:dict";


    /**
     * 字典翻译文本后缀
     */
    public static final String DICT_TEXT_SUFFIX = "_dictText";

}
