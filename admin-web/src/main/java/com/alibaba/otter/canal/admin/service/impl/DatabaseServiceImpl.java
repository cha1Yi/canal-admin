package com.alibaba.otter.canal.admin.service.impl;

import com.alibaba.otter.canal.admin.model.Database;
import com.alibaba.otter.canal.admin.model.Pager;
import com.alibaba.otter.canal.admin.service.DatabaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wuxuan.chai
 * @date 2021/4/30 3:02 下午
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

    @Override
    public List<Database> list() {
        return Database.find.query().findList();
    }

    @Override
    public void add(Database database) {
        database.save();
    }

    @Override
    public Database getById(Long id) {
        return Database.find.byId(id);
    }

    @Override
    public void update(Database database) {
        database.update();
    }

    @Override
    public Pager<Database> page(Database database, Pager<Database> pager) {

        return null;
    }
}
