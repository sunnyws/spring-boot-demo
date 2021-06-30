package com.sunnyws.redis.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * - @Description:  TestController
 * - @Author qinlang
 * - @Date 2021/6/15
 * - @Version 1.0
 **/

@Slf4j
@RestController
public class TestController {

    @Resource
    private RedisTemplate redisTemplate;

    private static final String RANK_PREFIX = "user_rank";

    //zadd
    @GetMapping("/updateRank")
    public void updateRank() {
        for(int i = 0;i<20;i++){
            // 因为zset默认积分小的在前面，所以我们对score进行取反，这样用户的积分越大，对应的score越小，排名越高
            redisTemplate.opsForZSet().add(RANK_PREFIX,i,-i);
        }

    }

    //降序  score大的在前面  zrevrange
    @GetMapping("/revRange")
    public void revRange(String start, String end) {
        Set<Integer> result = redisTemplate.opsForZSet().reverseRange(RANK_PREFIX, Long.valueOf(start), Long.valueOf(end));
        for (Integer sub : result) {
            log.info(String.valueOf(sub));
        }
    }

    //升序  score小的在前面  zrange
    @GetMapping("/zRange")
    public void zrange  (String start, String end) {
        Set<Integer> result = redisTemplate.opsForZSet().range(RANK_PREFIX, Long.valueOf(start), Long.valueOf(end));
        for (Integer sub : result) {
            log.info(String.valueOf(sub));
        }
    }

    //rank 判断value在zset中的排名  升序
    @GetMapping("/zRank")
    public Long zRank  (Long value) {
        return redisTemplate.opsForZSet().rank(RANK_PREFIX,value)+1;
    }

    //rank 判断value在zset中的排名  降序
    @GetMapping("/zrevRank")
    public Long zrevRank  (Long value) {
        return redisTemplate.opsForZSet().reverseRank(RANK_PREFIX,value)-1;
    }

    //降序  score大的在前面  zrevrange
    @GetMapping("/rangeWithScore")
    public void rangeWithScore(Integer n) {
        Set<ZSetOperations.TypedTuple<Integer>>  result = redisTemplate.opsForZSet().rangeWithScores(RANK_PREFIX, 0, n-1);
        for (ZSetOperations.TypedTuple<Integer> sub : result) {
            log.info(sub.getValue()+": "+sub.getScore());
        }
    }
}
