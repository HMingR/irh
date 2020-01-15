package top.imuster.goods.service.impl;


import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.core.annotation.MqGenerate;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.goods.api.pojo.ProductMessage;
import top.imuster.goods.dao.ProductMessageDao;
import top.imuster.goods.service.ProductMessageService;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * ProductMessageService 实现类
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
@Service("productMessageService")
public class ProductMessageServiceImpl extends BaseServiceImpl<ProductMessage, Long> implements ProductMessageService {

    @Resource
    private ProductMessageDao productMessageDao;

    @Override
    public BaseDao<ProductMessage, Long> getDao() {
        return this.productMessageDao;
    }

    @Override
    public List<ProductMessage> generateMessageTree(Long id) {
        ProductMessage condition = new ProductMessage();
        condition.setState(2);
        condition.setProductId(id);
        List<ProductMessage> allMessage = productMessageDao.selectEntryList(condition);
        List<ProductMessage> tree = generateTree(allMessage);
        return tree;
    }

    @Override
    public void generateSendMessage(SendMessageDto sendMessageDto, Long parentId) {
        sendMessageDto.setType("EMAIL");
        if(parentId == 0){
            sendMessageDto.setSourceType(30);
            sendMessageDto.setSourceId(-1L);
            sendMessageDto.setTopic("商品留言");
            sendMessageDto.setValue("有人对你的商品进行了留言,快来看看吧!");
            //sendMessageDto.setTargetId();
        }else {
            //sendMessageDto.setSourceId();
        }
    }

    @Override
    @MqGenerate(one = false)
    public void test(List<SendMessageDto> sendMessageDtos) {
        SendMessageDto sendMessageDto1 = new SendMessageDto();
        sendMessageDto1.setTargetId(1L);
        SendMessageDto sendMessageDto2 = new SendMessageDto();
        sendMessageDto2.setTargetId(2L);
        SendMessageDto sendMessageDto3 = new SendMessageDto();
        sendMessageDto3.setTargetId(3L );
        sendMessageDtos.add(sendMessageDto1);
        sendMessageDtos.add(sendMessageDto2);
        sendMessageDtos.add(sendMessageDto3);
        System.out.println("asdf");
    }

    public static void main(String[] args) throws Exception {
        Class<?> aClass = Class.forName("top.imuster.goods.service.impl.ProductMessageServiceImpl");
        Method test = aClass.getMethod("test", List.class);
        Parameter[] parameters = test.getParameters();

        for(Parameter p : parameters){
            Type t = p.getParameterizedType();
            ParameterizedType pt = (ParameterizedType) t;
            Class actualTypeArgument = (Class)pt.getActualTypeArguments()[0];
            if(actualTypeArgument.isAssignableFrom(SendMessageDto.class)){
                System.out.println("asdf");
                System.out.println(actualTypeArgument.getName());
                System.out.println("asdfaasdf" + p.getName());
                System.out.println("值为" + actualTypeArgument);
            }
            System.out.println(actualTypeArgument);
        }
    }

    private List<ProductMessage> generateTree(List<ProductMessage> list){
        List<ProductMessage> result = new ArrayList<>();
        // 1、获取第一级节点
        for (ProductMessage productMessage : list) {
            if(0 == productMessage.getParentId()) {
                result.add(productMessage);
            }
        }
        // 2、递归获取子节点
        for (ProductMessage parent : result) {
            parent = recursiveTree(parent, list);
        }
        return result;
    }

    private ProductMessage recursiveTree(ProductMessage parent, List<ProductMessage> list) {
        for (ProductMessage productMessage : list) {
            if(parent.getId() == productMessage.getParentId()) {
                productMessage = recursiveTree(productMessage, list);
                parent.getChilds().add(productMessage);
            }
        }
        return parent;
    }
}