package top.imuster.message.provider.service.impl;

import cn.hutool.core.lang.Assert;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.core.enums.OperationType;
import top.imuster.message.provider.exception.MessageException;

import java.io.IOException;

/**
 * @ClassName: EsOperationService
 * @Description: EsOperationService
 * @author: hmr
 * @date: 2020/5/6 11:01
 */
@Service("esOperationService")
public class EsOperationService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());


    private String index;

    private String id;

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    private String getIndex() {
        return index;
    }

    private void setIndex(String index) {
        this.index = index;
    }

    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    private String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    private void executeByOperationType(String jsonObjectString, OperationType type) throws IOException {
        if(OperationType.INSERT.equals(type)) add2ES(jsonObjectString);
        if(OperationType.REMOVE.equals(type)) deleteById(id);
        if(OperationType.UPDATE.equals(type)) updateById(jsonObjectString);
    }

    private void add2ES(String jsonObjectString) throws IOException {
        IndexRequest indexRequest = new IndexRequest(index, index);
        indexRequest.source(jsonObjectString, XContentType.JSON).id(id);
        restHighLevelClient.index(indexRequest);
    }

    private void deleteById(String id) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest(index, index, id);
        restHighLevelClient.delete(deleteRequest);
    }

    private void updateById(String jsonObjectString){

    }

    public void execute(String jsonString, String id ,OperationType type, String index) throws IOException {
        Assert.notEmpty(jsonString, "jsonString不能为null");
        Assert.notNull(type, "操作类型不能为null");
        Assert.notNull(index, "ES索引名称不能为null");
        Assert.notEmpty(id, "id不能为空");
        if(OperationType.REMOVE.equals(type)){
            try{
                Long.parseLong(String.valueOf(jsonString));
            }catch (Exception e){
                throw new MessageException("参数错误");
            }
        }
        setRestHighLevelClient(restHighLevelClient);
        setIndex(index);
        setId(id);
        executeByOperationType(jsonString, type);
    }

}
