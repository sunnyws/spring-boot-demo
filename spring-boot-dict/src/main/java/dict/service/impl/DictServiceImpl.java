package dict.service.impl;

import dict.constant.CommonConstant;
import dict.entity.DictEntity;
import dict.service.DictService;
import dict.mapper.DictMapper;
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
    @Cacheable(value = CommonConstant.DICT_CACHE,key = "#code+':'+#key")
    public String queryDictTextByKey(String code, String key){
        log.info("无缓存时调用");
        return dictMapper.queryDictTextByKey(code,key);
    }

}
