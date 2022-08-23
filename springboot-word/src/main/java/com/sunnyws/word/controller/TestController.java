package com.sunnyws.word.controller;

import com.sunnyws.word.constant.WordType;
import com.sunnyws.word.service.ToCommentSensitiveWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * This class is for TestController
 *
 * @author ql
 * @date 2022/06/09
 */
@Slf4j
@RestController
public class TestController {

    @Resource
    private ToCommentSensitiveWordService toCommentSensitiveWordService;

    @GetMapping("/test")
    public void test(){
        String text = "利于上游行业杀人发展的政策逐渐发布";
        Boolean flag = toCommentSensitiveWordService.include(text);
        log.info(String.valueOf(flag));
    }

    @GetMapping("/add")
    public void add(){
        String text = "杀人";
        toCommentSensitiveWordService.addWord(Collections.singletonList(text), WordType.BLACK);
//        log.info(String.valueOf(flag));
    }

    @GetMapping("/delete")
    public void delete(){
        String text = "杀人";
        toCommentSensitiveWordService.removeWord(Collections.singletonList(text), WordType.BLACK);
//        log.info(String.valueOf(flag));
    }
}
