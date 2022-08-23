package com.sunnyws.word.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunnyws.word.entity.ToCommentSensitiveWord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * (ToCommentSensitiveWord)表数据库访问层
 *
 * @author qinlang
 * @since 2022-06-09 16:42:57
 */
@Mapper
public interface ToCommentSensitiveWordMapper extends BaseMapper<ToCommentSensitiveWord> {


    @Select("select word_name from to_comment_sensitive_word")
    List<String>  getAllListWord();
}