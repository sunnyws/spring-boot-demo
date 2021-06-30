package com.sunnyws.sharding.config;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Range;
import io.swagger.models.auth.In;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;

/**
 * - @Description:  TableRangeShardingAlgorithm
 * - @Author qinlang
 * - @Date 2021/6/26
 * - @Version 1.0
 **/

@Component
public class TableRangeShardingAlgorithm implements RangeShardingAlgorithm<Date> {

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<Date> rangeShardingValue) {
        Collection<String> collect = new LinkedHashSet<>(collection.size());
        Range<Date> valueRange = rangeShardingValue.getValueRange();


        Date startTime = valueRange.lowerEndpoint();
        Date endTime = valueRange.upperEndpoint();
        String start = DateUtil.format(startTime, "yyyyMM");
        String end =  DateUtil.format(endTime, "yyyyMM");
        String table_front = rangeShardingValue.getLogicTableName()+"_";
        for (String each : collection) {
            Integer index = Integer.valueOf(each.replace(table_front,""));
            if(index <= Integer.valueOf(end) && index >= Integer.valueOf(start)){
                collect.add(each);
            }
        }
        return collect;
    }


}
