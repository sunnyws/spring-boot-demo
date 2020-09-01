package dict.transformation;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dict.constant.CommonConstant;
import dict.entity.Result;
import dict.service.DictService;
import dict.utils.CommnotUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * - @Description:  CommntUtil
 * - @Author qinlang
 * - @Date 2020/7/14 15:17
 */
@Aspect
@Component
@Slf4j
public class DictAspect {

    @Autowired
    private DictService dictService;


    /**
     * 定义切点Pointcut拦截所有对服务器的请求
     */
    @Pointcut("execution( * dict.controller.*.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    	long time1=System.currentTimeMillis();	
        Object result = pjp.proceed();
        long time2=System.currentTimeMillis();
        log.info("获取JSON数据 耗时："+(time2-time1)+"ms");
        long start=System.currentTimeMillis();
        this.parseDictText(result);
        long end=System.currentTimeMillis();
        log.info("解析注入JSON数据  耗时"+(end-start)+"ms");
        return result;
    }

    /**
     * 本方法针对返回对象为Result的IPage的分页列表数据进行动态字典注入
     * 需要在使用字典的实体类上添加注解：@Dict(dicCode = "sex",dicText = "sex_dict")
     * dicCode---对应数据库字典表的属性名  | dicText   put到原始数据中key  为空则使用默认策略 dicCode + “_dictText”:
     * 前端无需转换 只需在 dictText 中获取属性值即可
     * @param result
     */
    private void parseDictText(Object result) {
        if (result instanceof Result) {
            if (((Result) result).getResult() instanceof IPage) {
                List<JSONObject> items = new ArrayList<>();
                for (Object record : ((IPage) ((Result) result).getResult()).getRecords()) {
                    ObjectMapper mapper = new ObjectMapper();
                    String json="{}";
                    try {
                        //解决@JsonFormat注解解析不了的问题详见SysAnnouncement类的@JsonFormat
                         json = mapper.writeValueAsString(record);
                    } catch (JsonProcessingException e) {
                        log.error("json解析失败"+e.getMessage(),e);
                    }
                    JSONObject item = JSONObject.parseObject(json);
                    //获取类的所有属性 包括父类
                    for (Field field : CommnotUtil.getAllFields(record)) {
                        //找到含有Dict注释的属性
                        if (field.getAnnotation(Dict.class) != null) {
                            String code = field.getAnnotation(Dict.class).dicCode();
                            String text = field.getAnnotation(Dict.class).dicText();
                            String key = String.valueOf(item.get(field.getName()));

                            //翻译字典值对应的txt
                            String textValue = translateDictValue(code,text,key);
                            if (!StringUtils.isBlank(text)) {
                                item.put(text, textValue);
                            } else {
                                //走默认策略
                                item.put(field.getName() + CommonConstant.DICT_TEXT_SUFFIX, textValue);
                            }
                        }
//                        //date类型默认转换string格式化日期
//                        if (field.getType().getName().equals("java.util.Date")&&field.getAnnotation(JsonFormat.class)==null&&item.get(field.getName())!=null){
//                            SimpleDateFormat aDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                            item.put(field.getName(), aDate.format(new Date((Long) item.get(field.getName()))));
//                        }
                    }
                    items.add(item);
                }
                ((IPage) ((Result) result).getResult()).setRecords(items);
            }

        }
    }

    /**
     *  翻译字典文本
     * @param code
     * @param text
     * @param key
     * @return
     */
    private String translateDictValue(String code, String text, String key) {
    	if(StringUtils.isEmpty(key)) {
    		return null;
    	}
        StringBuffer textValue=new StringBuffer();
        String[] keys = key.split(",");
        for (String k : keys) {
            String tmpValue = null;
            if (k.trim().length() == 0) {
                continue; //跳过循环
            }
            //通过查询指定code,和key 获取字典值text
            tmpValue = dictService.queryDictTextByKey(code, k.trim());
            log.info("DictAspect: "+ " dicText= "+text+" ,dicCode="+code);
            log.info("字典 key : "+ k + "   value:"+ tmpValue);

            if (tmpValue != null) {
                if (!"".equals(textValue.toString())) {
                    textValue.append(",");
                }
                textValue.append(tmpValue);
            }

        }
        return textValue.toString();
    }

}
