package com.sunnyws.excel.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.sunnyws.excel.service.UserService;
import com.sunnyws.excel.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * - @Description:  UserListener
 * - @Author qinlang
 * - @Date 2021/6/16
 * - @Version 1.0
 **/
@Slf4j
public class UserListener extends AnalysisEventListener<UserEntity> {

    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 2;

    List<UserEntity> list = new ArrayList<UserEntity>();

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private UserService userService;

    /**
     * 如果使用了spring,请使用这个构造方法。每次创建Listener的时候需要把spring管理的类传进来
     *
     */
    public UserListener(UserService userService) {
        this.userService = userService;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     */
    @Override
    public void invoke(UserEntity user, AnalysisContext analysisContext) {
        System.out.println("invoke方法被调用");
        log.info("解析到一条数据:{}", JSON.toJSONString(user));
        list.add(user);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }

    }
    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println("doAfterAllAnalysed方法 被调用");
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");

    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", list.size());
        userService.saveBatch(list);
        log.info("存储数据库成功！");
    }
}
