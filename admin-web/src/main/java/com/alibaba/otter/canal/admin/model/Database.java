package com.alibaba.otter.canal.admin.model;

import com.zaxxer.hikari.HikariDataSource;
import io.ebean.Finder;
import io.ebean.annotation.WhenCreated;
import io.ebean.annotation.WhenModified;
import io.ebean.config.dbplatform.DbType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.sql.DataSource;
import java.util.Date;

/**
 * 数据库实例
 *
 * @author wuxuan.chai
 * @date 2021/4/30 2:40 下午
 */
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "t_database")
@Data
public class Database extends Model {
    public static final DatabaseFinder find = new DatabaseFinder();

    public static class DatabaseFinder extends Finder<Long, Database> {
        public DatabaseFinder() {
            super(Database.class);
        }
    }

    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 数据库的名称
     */
    private String caption;

    private DatabaseType databaseType;

    /**
     * 数据库主机地址
     */
    private String host;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 数据库用户
     */
    private String username;

    /**
     * 数据库密码
     */
    private String password;

    /**
     * 数据库的schema信息
     */
    private String schema;

    /**
     * 额外属性
     */
    private String attrs;

    /**
     * 数据库的描述信息
     */
    private String description;

    /**
     * 创建时间
     */
    @WhenCreated
    private Date created;

    /**
     * 修改时间
     */
    @WhenModified
    private Date updated;


    @Getter
    @AllArgsConstructor
    public enum DatabaseType {
        /**
         * 数据库类型枚举
         */
        MYSQL("Mysql数据库", "com.mysql.cj.jdbc.Driver", "jdbc:mysql://%s:%s/%s?%s"),
        CLICKHOUSE("Clickhouse数据库", "ru.yandex.clickhouse.ClickHouseDriver", "jdbc:clickhouse://%s:%s/%s?%s");

        private final String desc;

        private final String driverClass;

        private final String jdbcUrlPattern;


    }

    /**
     * 转换数据源连接池
     *
     * @return 数据源链接池
     */
    public DataSource toDataSource() {
        final HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setUsername(username);
        hikariDataSource.setPassword(password);
        hikariDataSource.setDriverClassName(databaseType.getDriverClass());
        hikariDataSource.setJdbcUrl(String.format(databaseType.jdbcUrlPattern, host, port, schema, attrs));
        return hikariDataSource;
    }


}
