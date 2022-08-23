package com.sunnyws.word.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sunnyws.word.constant.WordType;
import com.sunnyws.word.entity.ToCommentSensitiveWord;

import java.util.List;

/**
 * (ToCommentSensitiveWord)表服务实现类
 *
 * @author qinlang
 * @since 2022-06-09 16:42:57
 */
public interface ToCommentSensitiveWordService extends IService<ToCommentSensitiveWord> {



    List<String> getAllListWord();
    /**
     * 是否包含敏感词
     *
     * @param text 输入文本
     */
    boolean include(final String text);

    /**
     * 替换敏感词
     *
     * @param text   输入文本
     * @param symbol 替换符号
     */
//    String replace(final String text, final char symbol);

    /**
     * 在线删除敏感词
     *
     * @param wordList 敏感词列表
     * @param wordType 黑名单 BLACk，白名单WHITE
     */
     void removeWord(Iterable<String> wordList, WordType wordType);

     void addWord(Iterable<String> wordList, WordType wordType);
}