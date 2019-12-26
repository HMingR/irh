package top.imuster.goods.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;

/**
 * @ClassName: GoodsServiceFeignApi
 * @Description: goods模块提供给其他模块调用的结构
 * @author: hmr
 * @date: 2019/12/26 20:34
 */
@FeignClient("goods-service")
public interface GoodsServiceFeignApi {

    /**
     * @Description: 分页条件查询二手商品
     * @Author: hmr
     * @Date: 2019/12/26 20:38
     * @param type
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @PostMapping("/goods/feign/es/list}")
    Message list(@RequestParam Integer currentPage, @RequestBody ProductInfo productInfo);
}
