package com.springboot.common.business;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class CommonJdbcUtil {

    private static DruidDataSource dataSource;

    public CommonJdbcUtil(String url, String username, String password) {
        dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(256);
        dataSource.setInitialSize(16);
        dataSource.setMaxWait(60000);
        dataSource.setMinIdle(16);
        dataSource.setTimeBetweenEvictionRunsMillis(3000);
        dataSource.setMinEvictableIdleTimeMillis(300000);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        if (url.contains("mysql")) {
            setDbType("mysql");
        } else if (url.contains("oracle")) {
            setDbType("oracle");
        } else if (url.contains("sqlserver")) {
            setDbType("sqlserver");
        } else if (url.contains("db2")) {
            setDbType("db2");
        }
    }

    public void setDbType(String dbType) {
        dataSource.setDbType(dbType);
    }

    public boolean isConnected() {
        boolean rst = true;
        try {
            DruidPooledConnection connection = dataSource.getConnection();
            dataSource.validateConnection(connection);
        } catch (SQLException e) {
            rst = false;
            System.out.println("数据库连接失败：" + e.getMessage());
        }

        return rst;
    }

    public DruidPooledConnection getConnection() throws SQLException {
        DruidPooledConnection connection = dataSource.getConnection();
        dataSource.validateConnection(connection);
        return connection;
    }

    public JdbcTemplate getJdbcTemplate() {
        if (isConnected()) {
            return new JdbcTemplate(dataSource);
        } else {
            throw new CommonException("数据库连接异常");
        }
    }

    public static void main(String[] args) {
        try {
            CommonJdbcUtil util = new CommonJdbcUtil("jdbc:mysql://localhost:3306/zhpt?useUnicode=true&characterEncoding=UTF-8&useSSL=false&&serverTimezone=UTC", "root", "root");
            List<Map<String, Object>> obj = util.getJdbcTemplate().queryForList("select * from cms_system_label");
            System.out.println(obj);
        } catch (Exception e) {
            System.out.println("出错了:" + e.getMessage());
        }
    }

    public void close() {
        dataSource.close();
    }
}
