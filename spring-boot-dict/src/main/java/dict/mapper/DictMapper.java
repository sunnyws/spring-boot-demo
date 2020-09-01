package dict.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import dict.entity.DictEntity;
import org.apache.ibatis.annotations.Param;

/**
 * @Description: 分类字典
 * @Author: qinlang
 * @Date:   2019-05-29
 * @Version: V1.0
 */

public interface DictMapper extends BaseMapper<DictEntity> {

     /**
      * 通过字典code获取字典数据
      * @param code
      * @param key
      * @return
      */
//     @Select(" select s.item_text from sys_dict_item s where s.dict_id = (select id from sys_dict where dict_code = #{code}) and s.item_value = #{key}")
     String queryDictTextByKey(@Param("code") String code, @Param("key") String key);

}