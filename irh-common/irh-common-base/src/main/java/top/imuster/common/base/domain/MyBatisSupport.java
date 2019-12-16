package top.imuster.common.base.domain;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.util.List;


public class MyBatisSupport {

    @Resource
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * @Description: 需要批量操作的时候可以使用这个方法
     * @Author: hmr
     * @Date: 2019/11/25 11:02
     * @param batchSqlSessionTemplate
     * @reture: void
     **/
    protected void changeToBatchExecuteType(@Qualifier("batchSqlSessionTemplate") SqlSessionTemplate batchSqlSessionTemplate){
        this.sqlSessionTemplate = batchSqlSessionTemplate;
    }

    protected int insert(String statement, Object parameter){
        int res = 0;
        try {
            res = sqlSessionTemplate.insert(statement, parameter);
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    protected int delete(String statement, Object parameter){
        int res = 0;
        try {
            res = sqlSessionTemplate.delete(statement, parameter);
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    protected int update(String statement, Object parameter){
        int res = 0;
        try {
            res = sqlSessionTemplate.update(statement, parameter);
        } catch (Exception e) {
            throw e;
        }
        return res;
    }

    protected <T> T select(String statement, Object parameter) {
        T obj = null;
        try {
            obj = (T) sqlSessionTemplate.selectOne(statement, parameter);
        } catch (Exception e) {
            throw e;
        }
        return obj;
    }

    protected <T> List<T> selectList(String statement, Object parameter){
        List<T> list = null;
        try {
            list = sqlSessionTemplate.selectList(statement, parameter);
        } catch (Exception e) {
            throw e;
        }
        return list;
    }
}
