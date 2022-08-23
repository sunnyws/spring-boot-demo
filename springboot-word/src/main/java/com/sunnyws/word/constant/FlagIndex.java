package com.sunnyws.word.constant;

import lombok.Data;

import java.util.List;

/**
 * 敏感词标记
 *
 * @author minghu.zhang
 */
@Data
public class FlagIndex {

    /**
     * 标记结果
     */
    private boolean flag;
    /**
     * 是否黑名单词汇
     */
    private boolean isWhiteWord;
    /**
     * 标记索引
     */
    private List<Integer> index;
}
