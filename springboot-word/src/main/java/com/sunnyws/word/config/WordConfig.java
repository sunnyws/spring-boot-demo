package com.sunnyws.word.config;

import com.sunnyws.word.constant.WordType;
import com.sunnyws.word.mapper.ToCommentSensitiveWordMapper;
import com.sunnyws.word.service.ToCommentSensitiveWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 词库上下文环境
 * <p>
 * 初始化敏感词库，将敏感词加入到HashMap中，构建DFA算法模型
 *
 * @author minghu.zhang
 */
@SuppressWarnings({"rawtypes", "unchecked"})
@Slf4j
@Component
public class WordConfig {


    @Resource
    private ToCommentSensitiveWordService toCommentSensitiveWordService;



    /**
     * 是否已初始化
     */
    private boolean init;


//    /**
//     * 获取初始化的敏感词列表
//     *
//     * @return 敏感词列表
//     */
//    public ConcurrentMap<String,String> getWordMap() {
//        return wordMap;
//    }

    /**
     * 初始化
     */
    @PostConstruct
    private synchronized void initKeyWord() {
        try {
            if (!init) {
                log.info("----------------开始初始化敏感词列表------------------");
                List<String> workList = toCommentSensitiveWordService.getAllListWord();
                // 将敏感词库加入到HashMap中
                toCommentSensitiveWordService.addWord(workList, WordType.BLACK);
                // 将非敏感词库也加入到HashMap中
//                addWord(readWordFile(whiteList), WordType.WHITE);
                log.info("----------------初始化敏感词列表完成------------------");
            }
            init = true;
        } catch (Exception e) {
            log.error("敏感词初始化失败！",e);
            throw new RuntimeException("敏感词初始化失败!");
        }
    }
//
//    /**
//     * 读取敏感词库，将敏感词放入HashSet中，构建一个DFA算法模型：<br>
//     * 中 = { isEnd = 0 国 = {<br>
//     * isEnd = 1 人 = {isEnd = 0 民 = {isEnd = 1} } 男 = { isEnd = 0 人 = { isEnd = 1 }
//     * } } } 五 = { isEnd = 0 星 = { isEnd = 0 红 = { isEnd = 0 旗 = { isEnd = 1 } } } }
//     */
//    public void addWord(Iterable<String> wordList, WordType wordType) {
//        ConcurrentMap<String,String> map = new ConcurrentHashMap<>(1024);
//        Map nowMap;
//        Map<String, String> newWorMap;
//        // 迭代keyWordSet
//        for (String key : wordList) {
//            nowMap = map;
//            for (int i = 0; i < key.length(); i++) {
//                // 转换成char型
//                char keyChar = key.charAt(i);
//                // 获取
//                Object wordMap = nowMap.get(String.valueOf(keyChar));
//                // 如果存在该key，直接赋值
//                if (wordMap != null) {
//                    nowMap = (Map) wordMap;
//                } else {
//                    // 不存在则构建一个map，同时将isEnd设置为0，因为他不是最后一个
//                    newWorMap = new HashMap<>(4);
//                    // 不是最后一个
//                    newWorMap.put("isEnd", String.valueOf(EndType.HAS_NEXT.ordinal()));
//                    nowMap.put(String.valueOf(keyChar), newWorMap);
//                    nowMap = newWorMap;
//                }
//
//                if (i == key.length() - 1) {
//                    // 最后一个
//                    nowMap.put("isEnd", String.valueOf(EndType.IS_END.ordinal()));
//                    nowMap.put("isWhiteWord", String.valueOf(wordType.ordinal()));
//                }
//            }
//        }
//        redisTemplate.opsForHash().putAll("word:type1",map);
//
//    }

//    /**
//     * 在线删除敏感词
//     *
//     * @param wordList 敏感词列表
//     * @param wordType 黑名单 BLACk，白名单WHITE
//     */
//    public void removeWord(Iterable<String> wordList, WordType wordType) {
//        Map nowMap;
//        for (String key : wordList) {
//            List<Map> cacheList = new ArrayList<>();
//            nowMap = wordMap;
//            for (int i = 0; i < key.length(); i++) {
//                char keyChar = key.charAt(i);
//
//                Object map = nowMap.get(keyChar);
//                if (map != null) {
//                    nowMap = (Map) map;
//                    cacheList.add(nowMap);
//                } else {
//                    return;
//                }
//
//                if (i == key.length() - 1) {
//                    char[] keys = key.toCharArray();
//                    boolean cleanable = false;
//                    char lastChar = 0;
//                    for (int j = cacheList.size() - 1; j >= 0; j--) {
//                        Map cacheMap = cacheList.get(j);
//                        if (j == cacheList.size() - 1) {
//                            if (String.valueOf(WordType.BLACK.ordinal()).equals(cacheMap.get("isWhiteWord"))) {
//                                if (wordType == WordType.WHITE) {
//                                    return;
//                                }
//                            }
//                            if (String.valueOf(WordType.WHITE.ordinal()).equals(cacheMap.get("isWhiteWord"))) {
//                                if (wordType == WordType.BLACK) {
//                                    return;
//                                }
//                            }
//                            cacheMap.remove("isWhiteWord");
//                            cacheMap.remove("isEnd");
//                            if (cacheMap.size() == 0) {
//                                cleanable = true;
//                                continue;
//                            }
//                        }
//                        if (cleanable) {
//                            Object isEnd = cacheMap.get("isEnd");
//                            if (String.valueOf(EndType.IS_END.ordinal()).equals(isEnd)) {
//                                cleanable = false;
//                            }
//                            cacheMap.remove(lastChar);
//                        }
//                        lastChar = keys[j];
//                    }
//
//                    if (cleanable) {
//                        wordMap.remove(lastChar);
//                    }
//                }
//            }
//        }
//    }

//    /**
//     * 读取敏感词库中的内容，将内容添加到set集合中
//     */
//    private Set<String> readWordFile(String file) throws Exception {
//        Set<String> set;
//        // 字符编码
//        String encoding = "UTF-8";
//        try (InputStreamReader read = new InputStreamReader(
//                this.getClass().getResourceAsStream(file), encoding)) {
//            set = new HashSet<>();
//            BufferedReader bufferedReader = new BufferedReader(read);
//            String txt;
//            // 读取文件，将文件内容放入到set中
//            while ((txt = bufferedReader.readLine()) != null) {
//                set.add(txt);
//            }
//        }
//        // 关闭文件流
//        return set;
//    }
}
