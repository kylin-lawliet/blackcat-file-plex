package com.blackcat.scaffolding.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.mapping.VendorDatabaseIdProvider;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * mybatis 配置类
 * @author : zhangdahui  2024/8/26 上午11:01
 */
@Configuration
@MapperScan("com.blackcat.scaffolding.dao")
public class MybatisConfig {

    // 分页配置指定数据库类型
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(DataSource dataSource, DatabaseIdProvider databaseIdProvider) throws SQLException {
        // MP插件
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        // 获取当前数据源对应的数据库类型，添加分页插件
        String databaseId = databaseIdProvider.getDatabaseId(dataSource);
        DbType dbType = DbType.getDbType(databaseId);
        paginationInnerInterceptor.setDbType(dbType);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

    /**
     * 自动识别使用的数据库类型
     * 在mapper.xml中databaseId的值就是跟这里对应，如果没有databaseId选择则说明该sql适用所有数据库
     */
    @Bean
    public DatabaseIdProvider getDatabaseIdProvider(){
        DatabaseIdProvider databaseIdProvider = new VendorDatabaseIdProvider();
        Properties properties = new Properties();
        // 第二个参数对应mapper.xml中databaseId的值
        properties.setProperty("Oracle",DbType.ORACLE.getDb());
        properties.setProperty("MySQL",DbType.MYSQL.getDb());
        properties.setProperty("PostgreSQL",DbType.POSTGRE_SQL.getDb());
        properties.setProperty("SqlServer",DbType.SQL_SERVER.getDb());
        databaseIdProvider.setProperties(properties);
        return databaseIdProvider;
    }

}
