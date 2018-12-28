package io.github.firefang.power.page.dialect;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.firefang.power.page.dialect.impl.HsqldbDialect;
import io.github.firefang.power.page.dialect.impl.MySqlDialect;

public class DialectUtil {
    private static final Logger log = LoggerFactory.getLogger(DialectUtil.class);
    private static final Map<String, Class<?>> DIALECT_ALIAS_MAP = new HashMap<String, Class<?>>(2);
    private static final Map<DataSource, IDialect> DATASOURCE_DIALECT_MAP = new ConcurrentHashMap<>(2);
    // 多数据源时，获取jdbcurl后是否关闭数据源
    private static boolean closeConn = true;

    static {
        // 注册别名
        DIALECT_ALIAS_MAP.put("mysql", MySqlDialect.class);
        DIALECT_ALIAS_MAP.put("h2", HsqldbDialect.class);
    }

    private DialectUtil() {
    }

    /**
     * 根据 DataSource 获取数据库方言
     * 
     * @param dataSource
     * @return
     */
    public static IDialect getDialect(DataSource dataSource) throws Exception {
        IDialect dialect = DATASOURCE_DIALECT_MAP.get(dataSource);
        if (dialect == null) {
            String dbname = getDatabaseProductName(dataSource);
            Class<?> clazz = fromDatabaseProductName(dbname);
            dialect = (IDialect) clazz.newInstance();
            DATASOURCE_DIALECT_MAP.put(dataSource, dialect);
        }
        return dialect;
    }

    /**
     * 获取数据库产品名称
     * 
     * @param dataSource
     * @return
     */
    private static String getDatabaseProductName(DataSource dataSource) {
        Connection conn = null;
        String dbname = null;
        String jdbcUrl = null;
        try {
            conn = dataSource.getConnection();
            DatabaseMetaData dbMetaData = conn.getMetaData();
            jdbcUrl = dbMetaData.getURL();
            log.trace("JDBC Version:{}.{}", dbMetaData.getJDBCMajorVersion(), dbMetaData.getJDBCMinorVersion());
            log.trace("Driver Version:{}.{}", dbMetaData.getDriverMajorVersion(), dbMetaData.getDriverMinorVersion());
            // 数据库产品名称,用它来区分数据库类型
            dbname = dbMetaData.getDatabaseProductName().toLowerCase();
            log.trace("Database Product Name:{}", dbname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) {
                try {
                    if (closeConn) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    // ignore
                }
            }
        }
        if (dbname == null || dbname.length() == 0) {
            log.error("database product name is empty from jdbcUrl:{}", jdbcUrl);
            throw new RuntimeException("无法自动获取DatabaseProductName!");
        }
        return dbname;
    }

    /**
     * 根据数据库产品名称获取对应方言Class
     * 
     * @param dbname
     * @return
     */
    private static Class<?> fromDatabaseProductName(String dbname) {
        Class<?> clazz = DIALECT_ALIAS_MAP.get(dbname);
        if (clazz == null) {
            throw new RuntimeException("无法自动获取数据库类型! DatabaseProductName:" + dbname);
        }
        return clazz;
    }

}
