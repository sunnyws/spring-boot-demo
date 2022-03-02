package com.sunnyws.current.limit.aspect;

/**
 * - @Description:  LimitAspect
 * - @Author qinlang
 * - @Date 2021/8/26
 * - @Version 1.0
 **/

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.RateLimiter;
import com.sunnyws.current.limit.utils.RateLimitUtil;
import com.sunnyws.current.limit.annotation.Limit;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * @Description 基于Guava限流
 * @Author jie.zhao
 * @Date 2019/8/19 9:55
 */
@Slf4j
@Aspect
@Component
public class LimitAspect {

    /**
     * 缓存
     * maximumSize 设置缓存个数
     * expireAfterWrite 写入后过期时间
     */
    private static LoadingCache<String, RateLimiter> limitCaches = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .build(new CacheLoader<String, RateLimiter>() {
                @Override
                public RateLimiter load(String key) throws Exception {
                    double perSecond = RateLimitUtil.getCacheKeyPerSecond(key);
                    return RateLimiter.create(perSecond);
                }
            });


    @Pointcut("@annotation(com.sunnyws.current.limit.annotation.Limit)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        //获取目标方法
        MethodSignature signature = (MethodSignature) point.getSignature();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Method method = signature.getMethod();
        if (method.isAnnotationPresent(Limit.class)) {
            String cacheKey = RateLimitUtil.generateCacheKey(method, request);
            RateLimiter limiter = limitCaches.get(cacheKey);
            if (!limiter.tryAcquire(1,1,TimeUnit.SECONDS)) {
                throw new RuntimeException("【限流】这位小同志的手速太快了");
            }
        }
        return point.proceed();
    }

}