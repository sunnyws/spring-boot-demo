package com.sunnyws.dict.service.impl;

import com.sunnyws.dict.constant.CacheConstant;
import com.sunnyws.dict.entity.DictEntity;
import com.sunnyws.dict.mapper.DictMapper;
import com.sunnyws.dict.service.DictService;
import com.sunnyws.dict.constant.CommonConstant;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
- @Description:   ServiceImpl
- @author generator
- @since 2020-07-10 11:22:05
*/
@Slf4j
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, DictEntity> implements DictService {

    @Autowired
    private DictMapper dictMapper;


    /**
     * 通过查询指定code 获取字典值text
     * @param code
     * @param key
     * @return
     */
    @Override
    @Cacheable(value = CacheConstant.DICT_CACHE,key = "#code+':'+#key")
    public String queryDictTextByKey(String code, String key){
        log.info("无缓存时调用");
        return dictMapper.queryDictTextByKey(code,key);
    }

}
