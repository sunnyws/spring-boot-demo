package com.sunnyws.current.limit.Enum;

/**
 * - @Description:  LimitKeyTypeEnum
 * - @Author qinlang
 * - @Date 2021/8/26
 * - @Version 1.0
 **/

public enum  LimitKeyTypeEnum {
    IPADDR("IPADDR", "根据Ip地址来限制"),
    CUSTOM("CUSTOM", "自定义根据业务唯一码来限制，需要在请求参数中添加 String limitKeyValue");

    private String keyType;
    private String desc;

    LimitKeyTypeEnum(String keyType, String desc) {
        this.keyType = keyType;
        this.desc = desc;
    }

    public String getKeyType() {
        return keyType;
    }

    public String getDesc() {
        return desc;
    }
}
