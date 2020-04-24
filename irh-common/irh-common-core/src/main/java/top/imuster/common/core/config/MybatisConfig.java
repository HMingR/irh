package top.imuster.common.core.config;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @ClassName: MybatisConfig
 * @Description: 创建sqlSessionTemplate，用于执行sql
 * @author: hmr
 * @date: 2019/11/25 10:32
 */
@Configuration
public class MybatisConfig {

    @Autowired
    DataSource druidDataSource;

    @Autowired
    SqlSessionFactory sqlSessionFactory;

    /**
     * @Description:
     * @Author: hmr
     * @Date: 2019/11/25 10:59
     * @param sqlSessionFactory
     * @reture: org.mybatis.spring.SqlSessionTemplate
     **/
    @Bean("batchSqlSessionTemplate")
    public SqlSessionTemplate getBatchSqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory, ExecutorType.BATCH);
    }

}
