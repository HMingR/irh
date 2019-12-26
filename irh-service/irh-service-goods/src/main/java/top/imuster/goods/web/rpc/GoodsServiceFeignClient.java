package top.imuster.goods.web.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.goods.config.GoodsException;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: GoodsServiceFeignClient
 * @Description: 商品模块提供的接口实现类
 * @author: hmr
 * @date: 2019/12/26 20:41
 */
@RestController
@RequestMapping("/goods/feign")
@Slf4j
public class GoodsServiceFeignClient implements GoodsServiceFeignApi {

    @Resource
    ProductInfoService productInfoService;

    @Override
    @PostMapping("/es/list")
    public Message list(@RequestParam Integer currentPage, @RequestBody ProductInfo productInfo) {
        try{
            Page<ProductInfo> page = new Page<>();
            page.setCurrentPage(currentPage);
            Page<ProductInfo> productInfoPage = productInfoService.selectPage(productInfo, page);
            return Message.createBySuccess(productInfoPage);
        }catch (Exception e){
            log.error("商品模块的feign代理出现异常", e.getMessage(), e);
            throw new GoodsException("商品模块的feign代理出现异常"+ e.getMessage());
        }
    }
}
