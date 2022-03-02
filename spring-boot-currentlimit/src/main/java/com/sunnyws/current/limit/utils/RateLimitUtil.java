package com.sunnyws.current.limit.utils;

import com.sunnyws.current.limit.annotation.Limit;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * - @Description:  RateLimitUtil
 * - @Author qinlang
 * - @Date 2021/8/26
 * - @Version 1.0
 **/

public class RateLimitUtil {

    public static String generateCacheKey(Method method, HttpServletRequest request) {
        //获取方法上的注解
        Limit limit = method.getAnnotation(Limit.class);
        StringBuffer cacheKey = new StringBuffer(limit.name() + ":");
        switch (limit.limitKeyType()) {
            case IPADDR:
                cacheKey.append(getIpAddr(request) + ":");
                break;
            case CUSTOM:
                String limitKeyValue = request.getParameter("limitKeyValue");
                if (StringUtils.isEmpty(limitKeyValue)) {
                    throw new RuntimeException("【" + method.getName() + "】自定义业务Key缺少参数String limitKeyValue，或者参数为空");
                }
                cacheKey.append(limitKeyValue + ":");
                break;
        }
        cacheKey.append(limit.perSecond());
        return cacheKey.toString();
    }

    /**
     * 获取缓存key的限制每秒访问次数
     * <p>
     * 规则 资源名:业务key:perSecond
     *
     * @param cacheKey
     * @return
     */
    public static double getCacheKeyPerSecond(String cacheKey) {
        String perSecond = cacheKey.split(":")[2];
        return Double.parseDouble(perSecond);
    }

    /**
     * 获取客户端IP地址
     *
     * @param request 请求
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        if ("0:0:0:0:0:0:0:1".equals(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }
}
