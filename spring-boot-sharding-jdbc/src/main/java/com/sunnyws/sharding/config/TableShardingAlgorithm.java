package com.sunnyws.sharding.config;

import cn.hutool.core.date.DateUtil;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;

/**
 * - @Description:  TableShardingAlgorithm
 * - @Author qinlang
 * - @Date 2021/6/26
 * - @Version 1.0
 **/

@Component
public class TableShardingAlgorithm implements PreciseShardingAlgorithm<Long> {

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> preciseShardingValue) {
        // 基本的表名_年份月份  base_199001
        Date date = DateUtil.parse(String.valueOf(preciseShardingValue.getValue()));
        String tableDate = DateUtil.format(date, "yyyyMM");
//        String targetTable = preciseShardingValue.getLogicTableName() + "_" + LocalDate.now().format(formatter);
        String targetTable = preciseShardingValue.getLogicTableName() + "_" + tableDate;
        if (availableTargetNames.contains(targetTable)){
            return targetTable;
        }
        throw new UnsupportedOperationException("无效的表名称: " + targetTable);
    }
}