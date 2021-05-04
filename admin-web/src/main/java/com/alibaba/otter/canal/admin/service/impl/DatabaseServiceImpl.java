package com.alibaba.otter.canal.admin.service.impl;

import com.alibaba.otter.canal.admin.model.Database;
import com.alibaba.otter.canal.admin.model.Pager;
import com.alibaba.otter.canal.admin.service.DatabaseService;
import io.ebean.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
        final Query<Database> databaseQuery = baseDatabaseQuery(database);
        final Query<Database> databaseQueryCopy = databaseQuery.copy();
        final int count = databaseQueryCopy.findCount();
        pager.setCount((long) count);
        final List<Database> databaseList = databaseQueryCopy.order()
                .desc("id")
                .setFirstRow(pager.getOffset().intValue())
                .setMaxRows(pager.getSize())
                .findList();

        //todo 数据源校验
        pager.setItems(databaseList);

        return pager;
    }

    private Query<Database> baseDatabaseQuery(Database database) {
        final Query<Database> query = Database.find.query();
        final Query<Database> databaseQuery = query.select("caption,description,databaseType").setDisableLazyLoading(true);
        if (database == null) {
            return null;
        }

        final Database.DatabaseType databaseType = database.getDatabaseType();

        if (!StringUtils.isEmpty(databaseType)) {
            databaseQuery.where().eq("databaseType", databaseType.name());
        }
        final String caption = database.getCaption();
        if (!StringUtils.isEmpty(caption)) {
            databaseQuery.where().like("caption", "%" + caption + "%");
        }
        return databaseQuery;
    }
}
