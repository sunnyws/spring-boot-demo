package dict.service;


import com.baomidou.mybatisplus.extension.service.IService;
import dict.entity.DictEntity;
import org.apache.ibatis.annotations.Param;

/**
- @Description:  Service
- @author generator
- @since 2020-07-10 11:22:05
  */
public interface DictService extends IService<DictEntity> {

     /**
      * 通过查询指定code 获取字典值text
      * @param code
      * @param key
      * @return
      */
     String queryDictTextByKey(String code, String key);
}