package top.imuster.goods.web.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.service.GoodsDemandServiceFeignApi;
import top.imuster.goods.service.ProductDemandInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: GoodsDemandServiceFeignClient
 * @Description: GoodsDemandServiceFeignClient
 * @author: hmr
 * @date: 2020/2/7 17:19
 */
@RestController
@RequestMapping("/goods/feign/demand")
public class GoodsDemandServiceFeignClient implements GoodsDemandServiceFeignApi {

    private static final Logger log = LoggerFactory.getLogger(GoodsDemandServiceFeignClient.class);

    @Resource
    ProductDemandInfoService productDemandInfoService;

    @Override
    @GetMapping("{id}")
    public Message<ProductDemandInfo> getDemandById(@PathVariable("id") Long id) {
        ProductDemandInfo productDemandInfo = productDemandInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(productDemandInfo);
    }
}
