package top.imuster.common.core.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
}
