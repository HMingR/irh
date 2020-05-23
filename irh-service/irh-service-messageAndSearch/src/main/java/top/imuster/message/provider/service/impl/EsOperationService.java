package top.imuster.message.provider.service.impl;

import cn.hutool.core.lang.Assert;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.core.enums.OperationType;
import top.imuster.message.provider.exception.MessageException;

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
    private TransportClient transportClient;

    private String getIndex() {
        return index;
    }

    private void setIndex(String index) {
        this.index = index;
    }

    private TransportClient getTransportClient() {
        return transportClient;
    }

    private void setTransportClient(TransportClient transportClient) {
        this.transportClient = transportClient;
    }

    private String getId() {
        return id;
    }

    private void setId(String id) {
        this.id = id;
    }

    private void executeByOperationType(String jsonObjectString, OperationType type){
        if(OperationType.INSERT.equals(type)) add2ES(jsonObjectString);
        if(OperationType.REMOVE.equals(type)) deleteById(id);
        if(OperationType.UPDATE.equals(type)) updateById(jsonObjectString);
    }

    private void add2ES(String jsonObjectString){
        IndexResponse indexResponse = transportClient.prepareIndex(getIndex(), getIndex()).setSource(jsonObjectString).setId(id).get();
        log.debug("插入的值为{}", indexResponse.getId());
    }

    private void deleteById(String id){
        transportClient.prepareDelete(getIndex(), getIndex(), id).execute();
    }

    private void updateById(String jsonObjectString){
        transportClient.prepareUpdate(getIndex(), getIndex(), getId()).setDoc(jsonObjectString);
    }

    public void execute(String jsonString, String id ,OperationType type, String index){
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
        setTransportClient(transportClient);
        setIndex(index);
        setId(id);
        executeByOperationType(jsonString, type);
    }

}
