package top.imuster.goods.web.rpc;

import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.UserException;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.dto.ProductInfoDto;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.goods.exception.GoodsException;
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
    @PostMapping(value = "/es/list")
    public Message list(@RequestBody ProductInfoDto productInfoDto) {
        try{
            Page<ProductInfo> page = productInfoDto.getPage();
            ProductInfo productInfo = productInfoDto.getProductInfo();
            Page<ProductInfo> productInfoPage = productInfoService.selectPage(productInfo, page);
            return Message.createBySuccess(productInfoPage);
        }catch (Exception e){
            log.error("商品模块的feign代理出现异常", e.getMessage(), e);
            throw new GoodsException("商品模块的feign代理出现异常"+ e.getMessage());
        }
    }

    @Override
    @DeleteMapping("/es/{id}")
    public Message delProduct(@PathVariable("id") Long id) throws GoodsException {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(id);
        productInfo.setState(1);
        int i = productInfoService.updateByKey(productInfo);
        if(i != 1){
            return Message.createByError("删除失败,未找到该记录,请刷新后重试");
        }
        return Message.createBySuccess("操作成功");
    }

    @Override
    @GetMapping("/es/lockStock/{productId}")
    public boolean lockStock(@PathVariable("productId") Long productId) throws GoodsException {
        try{
            ProductInfo productInfo = new ProductInfo();
            productInfo.setId(productId);
            productInfo.setState(2);
            Integer count = productInfoService.selectEntryListCount(productInfo);
            if(count == 0){
                return false;
            }
            productInfo.setState(3);
            int i = productInfoService.updateByKey(productInfo);
            if(i != 1){
                return false;
            }
            return true;
        }catch (Exception e){
            throw new GoodsException("锁定库存失败");
        }
    }
}
