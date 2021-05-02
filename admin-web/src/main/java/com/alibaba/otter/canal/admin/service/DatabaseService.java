package com.alibaba.otter.canal.admin.service;

import com.alibaba.otter.canal.admin.model.Database;
import com.alibaba.otter.canal.admin.model.Pager;

import java.util.List;

/**
 * @author wuxuan.chai
 * @date 2021/4/30 3:01 下午
 */
public interface DatabaseService {
    /**
     * 列表展示
     *
     * @return 列表
     */
    List<Database> list();

    /**
     * 添加数据库
     *
     * @param database 数据库实例
     */
    void add(Database database);

    /**
     * 根据id查询
     *
     * @param id 根据id查询
     * @return 查询后的结果
     */
    Database getById(Long id);

    /**
     * 修改database
     * @param database database改的实例
     */
    void update(Database database);


    Pager<Database> page(Database database, Pager<Database> pager);

}
