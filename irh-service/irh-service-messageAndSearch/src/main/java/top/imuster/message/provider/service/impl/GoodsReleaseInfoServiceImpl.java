package top.imuster.message.provider.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.enums.OperationType;
import top.imuster.message.provider.service.GoodsReleaseInfoService;

/**
 * @ClassName: GoodsReleaseInfoServiceImpl
 * @Description: GoodsReleaseInfoServiceImpl
 * @author: hmr
 * @date: 2020/5/6 10:29
 */
@Service("goodsReleaseInfoService")
public class GoodsReleaseInfoServiceImpl implements GoodsReleaseInfoService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    IndexRequestBuilder goodsIndexRequestBuilder;

    @Override
    public void executeByOperationType(BaseDomain info, OperationType type) {
        if(OperationType.INSERT.equals(type)) add2ES(info);
    }

    @Override
    public void add2ES(BaseDomain info) {
        goodsIndexRequestBuilder.setSource(info, "application/json");
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void updateById(BaseDomain info) {

    }


}
