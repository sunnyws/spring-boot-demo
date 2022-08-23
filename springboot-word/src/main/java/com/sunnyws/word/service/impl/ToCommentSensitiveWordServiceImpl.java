package com.sunnyws.word.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sunnyws.word.constant.EndType;
import com.sunnyws.word.constant.FlagIndex;
import com.sunnyws.word.constant.WordType;
import com.sunnyws.word.entity.ToCommentSensitiveWord;
import com.sunnyws.word.mapper.ToCommentSensitiveWordMapper;
import com.sunnyws.word.service.ToCommentSensitiveWordService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * (ToCommentSensitiveWord)表服务实现类
 *
 * @author qinlang
 * @since 2022-06-09 16:42:57
 */
@Service
public class ToCommentSensitiveWordServiceImpl extends ServiceImpl<ToCommentSensitiveWordMapper, ToCommentSensitiveWord> implements ToCommentSensitiveWordService {

    @Resource
    private RedisTemplate redisTemplate;


    @Resource
    private ToCommentSensitiveWordMapper toCommentSensitiveWordMapper;


    @Override
    public List<String>  getAllListWord(){
        return  toCommentSensitiveWordMapper.getAllListWord();
    }

    @Override
    public boolean include(final String text){
        return include(text, 0);
    }


//    @Override
//    public String replace(final String text, final char symbol) {
//        return replace(text, 0, symbol);
//    }

    @Override
    public void removeWord(Iterable<String> wordList, WordType wordType) {
        Map nowMap;
        Map<String,Object> wordMap = redisTemplate.opsForHash().entries("word:type1");
        for (String key : wordList) {
            List<Map> cacheList = new ArrayList<>();
            nowMap = wordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);

                Object map = nowMap.get(String.valueOf(keyChar));
                if (map != null) {
                    nowMap = (Map) map;
                    cacheList.add(nowMap);
                } else {
                    return;
                }

                if (i == key.length() - 1) {
                    char[] keys = key.toCharArray();
                    boolean cleanable = false;
                    char lastChar = 0;
                    for (int j = cacheList.size() - 1; j >= 0; j--) {
                        Map cacheMap = cacheList.get(j);
                        if (j == cacheList.size() - 1) {
                            if (String.valueOf(WordType.BLACK.ordinal()).equals(cacheMap.get("isWhiteWord"))) {
                                if (wordType == WordType.WHITE) {
                                    return;
                                }
                            }
                            if (String.valueOf(WordType.WHITE.ordinal()).equals(cacheMap.get("isWhiteWord"))) {
                                if (wordType == WordType.BLACK) {
                                    return;
                                }
                            }
                            cacheMap.remove("isWhiteWord");
                            cacheMap.remove("isEnd");
                            if (cacheMap.size() == 0) {
                                cleanable = true;
                                continue;
                            }
                        }
                        if (cleanable) {
                            Object isEnd = cacheMap.get("isEnd");
                            if (String.valueOf(EndType.IS_END.ordinal()).equals(isEnd)) {
                                cleanable = false;
                            }
                            cacheMap.remove(lastChar);
                        }
                        lastChar = keys[j];
                    }

                    if (cleanable) {
//                        wordMap.remove(lastChar);
                        redisTemplate.opsForHash().delete("word:type1", String.valueOf(lastChar));
                    }
                }
            }
        }
    }

    @Override
    public void addWord(Iterable<String> wordList, WordType wordType) {
        Map<String,String> map = new HashMap<>();
        Map nowMap;
        Map<String, String> newWorMap;
        // 迭代keyWordSet
        for (String key : wordList) {
            nowMap = map;
            for (int i = 0; i < key.length(); i++) {
                // 转换成char型
                char keyChar = key.charAt(i);
                // 获取
                Object wordMap = nowMap.get(String.valueOf(keyChar));
                // 如果存在该key，直接赋值
                if (wordMap != null) {
                    nowMap = (Map) wordMap;
                } else {
                    // 不存在则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<>(4);
                    // 不是最后一个
                    newWorMap.put("isEnd", String.valueOf(EndType.HAS_NEXT.ordinal()));
                    nowMap.put(String.valueOf(keyChar), newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    // 最后一个
                    nowMap.put("isEnd", String.valueOf(EndType.IS_END.ordinal()));
                    nowMap.put("isWhiteWord", String.valueOf(wordType.ordinal()));
                }
            }
        }
        redisTemplate.opsForHash().putAll("word:type1",map);
    }


    /**
     * 是否包含敏感词
     *
     * @param text 输入文本
     * @param skip 文本距离
     */
    private boolean include(final String text, final int skip) {
        Map current = redisTemplate.opsForHash().entries("word:type1");
        boolean include = false;
        char[] charset = text.toCharArray();
        for (int i = 0; i < charset.length; i++) {
            FlagIndex fi = getFlagIndex(charset, i, skip,current);
            if(fi.isFlag()) {
                if (fi.isWhiteWord()) {
                    i += fi.getIndex().size() - 1;
                } else {
                    include = true;
                    break;
                }
            }
        }
        return include;
    }

    /**
     * 获取标记索引
     *
     * @param charset 输入文本
     * @param begin   检测起始
     * @param skip    文本距离
     */
    private FlagIndex getFlagIndex(final char[] charset, final int begin, final int skip,Map current) {
        FlagIndex fi = new FlagIndex();
        boolean flag = false;
        int count = 0;
        List<Integer> index = new ArrayList<>();
        for (int i = begin; i < charset.length; i++) {
            char word = charset[i];
            Map mapTree = (Map) current.get(String.valueOf(word));
            if (count > skip || (i == begin && Objects.isNull(mapTree))) {
                break;
            }
            if (Objects.nonNull(mapTree)) {
                current = mapTree;
                count = 0;
                index.add(i);
            } else {
                count++;
                if (flag && count > skip) {
                    break;
                }
            }
            if ("1".equals(current.get("isEnd"))) {
                flag = true;
            }
            if ("1".equals(current.get("isWhiteWord"))) {
                fi.setWhiteWord(true);
                break;
            }
        }

        fi.setFlag(flag);
        fi.setIndex(index);

        return fi;
    }


//    /**
//     * 替换敏感词
//     *
//     * @param text   输入文本
//     * @param skip   文本距离
//     * @param symbol 替换符号
//     */
//    private String replace(final String text, final int skip, final char symbol) {
//        char[] charset = text.toCharArray();
//        for (int i = 0; i < charset.length; i++) {
//            FlagIndex fi = getFlagIndex(charset, i, skip);
//            if (fi.isFlag()) {
//                if (!fi.isWhiteWord()) {
//                    for (int j : fi.getIndex()) {
//                        charset[j] = symbol;
//                    }
//                } else {
//                    i += fi.getIndex().size() - 1;
//                }
//            }
//        }
//        return new String(charset);
//    }

}