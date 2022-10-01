package com.employee.employeedetails.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

@Configuration
public class DBDatasource {

    @Value("${mysql.url}")
    private String mysqlUrl;

    @Value("${mysql.username}")
    private String mysqlUsername;

    @Value("${mysql.driver-class-name}")
    private String mysqlDriver;

    @Value("${vertica.url}")
    private String verticaUrl;

    @Value("${vertica.username}")
    private String verticaUsername;

    @Value("${vertica.password}")
    private String verticaPassword;

    @Value("${vertica.driver-class-name}")
    private String verticaDriver;

    DataSource dataSource;

    @Bean(name = "mysql")
    public DataSource getMysqlDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(mysqlDriver);
        dataSourceBuilder.url(mysqlUrl);
        dataSourceBuilder.username(mysqlUsername);
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }

    public Connection getMysqlConnection(){
        try {
            return this.getMysqlDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "mysqlJdbc")
    public JdbcTemplate jdbcTemplateMysql(@Qualifier( "mysql") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
    @Bean(name = "vertica")
    public DataSource getVerticaDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(verticaDriver);
        dataSourceBuilder.url(verticaUrl);
        dataSourceBuilder.username(verticaUsername);
        dataSourceBuilder.password(verticaPassword);
        return dataSourceBuilder.build();
    }

    public Connection getVerticaConnection(){
        try {
            return this.getVerticaDataSource().getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "verticaJdbc")
    public JdbcTemplate jdbcTemplateVertica(@Qualifier( "vertica") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public HashMap<String, Connection> connectionMap(){
        HashMap<String, Connection> connectionHashMap = new HashMap<>();
        Connection mysqlConnection = getMysqlConnection();
        connectionHashMap.put("mysql",mysqlConnection);

        Connection verticaConnection = getVerticaConnection();
        connectionHashMap.put("vertica",verticaConnection);
        return connectionHashMap;
    }


}
