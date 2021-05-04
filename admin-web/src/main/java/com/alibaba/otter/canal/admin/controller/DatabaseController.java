package com.alibaba.otter.canal.admin.controller;

import com.alibaba.otter.canal.admin.model.BaseModel;
import com.alibaba.otter.canal.admin.model.Database;
import com.alibaba.otter.canal.admin.model.Pager;
import com.alibaba.otter.canal.admin.service.DatabaseService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author wuxuan.chai
 * @date 2021/4/30 3:36 下午
 */
@RestController
@RequestMapping("/api/{env}")
public class DatabaseController {

    @Resource
    private DatabaseService databaseService;

    @GetMapping("/databases/list")
    public BaseModel<List<Database>> list(@PathVariable String env) {
        return BaseModel.getInstance(databaseService.list());
    }


    @GetMapping("/databases")
    public BaseModel<Pager<Database>> page(@PathVariable String env, Database database, Pager<Database> pager) {
        return BaseModel.getInstance(databaseService.page(database, pager));
    }


    @PostMapping("/database")
    public BaseModel<String> add(Database database, @PathVariable String env) {
        databaseService.add(database);
        return BaseModel.getInstance("success");
    }

    @GetMapping("/database/{id}")
    public BaseModel<Database> getById(@PathVariable Long id, @PathVariable String env) {
        return BaseModel.getInstance(databaseService.getById(id));
    }

    @PutMapping("/database")
    public BaseModel<String> update(Database database, @PathVariable String env) {
        databaseService.update(database);
        return BaseModel.getInstance("success");
    }


    @DeleteMapping("/database/{id}")
    public BaseModel<String> delete(@PathVariable Long id, @PathVariable String env) {
        Database.find.deleteById(id);
        return BaseModel.getInstance("success");
    }

    @GetMapping("/database/dbType")
    public BaseModel<List<Database.DatabaseType>> getDatabaseTypes(@PathVariable String env) {
        return BaseModel.getInstance(Arrays.asList(Database.DatabaseType.values().clone()));
    }
}
