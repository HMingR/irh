package top.imuster.goods.web.rpc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductEvaluateInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.pojo.ProductMessage;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.goods.exception.GoodsException;
import top.imuster.goods.service.ProductEvaluateInfoService;
import top.imuster.goods.service.ProductInfoService;
import top.imuster.goods.service.ProductMessageService;
import top.imuster.user.api.pojo.ConsumerInfo;

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

    @Resource
    ProductMessageService productMessageService;

    @Resource
    ProductEvaluateInfoService productEvaluateInfoService;

    @Override
    @PostMapping(value = "/es/list")
    public Message<Page<ProductInfo>> list(@RequestBody Page<ProductInfo> page) {
        ProductInfo productInfo = page.getSearchCondition();
        Page<ProductInfo> productInfoPage = productInfoService.selectPage(productInfo, page);
        return Message.createBySuccess(productInfoPage);
    }

    @Override
    @DeleteMapping("/es/{id}")
    public Message<String> delProduct(@PathVariable("id") Long id) throws GoodsException {
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

    @Override
    @GetMapping("/es/stockOut/{productId}")
    public boolean productStockOut(@PathVariable("productId") Long productId) {
        try{
            ProductInfo productInfo = new ProductInfo();
            productInfo.setId(productId);
            productInfo.setState(2);
            productInfoService.updateByKey(productInfo);
            return true;
        }catch (Exception e){
            throw new GoodsException("商品下单失败,请稍后重试");
        }
    }

    @Override
    @DeleteMapping("/es/pm/{id}")
    public boolean deleteProductMessageById(@PathVariable("id") Long id) {
        ProductMessage condition = new ProductMessage();
        condition.setId(id);
        condition.setState(1);
        int i = productMessageService.updateByKey(condition);
        return i == 1;
    }

    @Override
    @DeleteMapping("/es/pe/{id}")
    public boolean deleteProductEvaluate(@PathVariable("id") Long id) {
        ProductEvaluateInfo condition = new ProductEvaluateInfo();
        condition.setState(1);
        condition.setId(id);
        int i = productEvaluateInfoService.updateByKey(condition);
        return i == 1;
    }

    /**
     * @Description 1-商品 2-留言 3-评价
     * @Date: 2020/1/17 11:01
     **/
    @Override
    @GetMapping("/es/{type}/{id}")
    public Long getConsumerIdByType(@PathVariable("id") Long id, @PathVariable("type") Integer type) {
        if(type == 1){
            ProductInfo productInfo = productInfoService.selectEntryList(id).get(0);
            return productInfo.getConsumerId();
        } else if(type == 2){
            ProductMessage productMessage = productMessageService.selectEntryList(id).get(0);
            return productMessage.getConsumerId();
        }else if(type == 3){
            ProductEvaluateInfo productEvaluateInfo = productEvaluateInfoService.selectEntryList(id).get(0);
            return productEvaluateInfo.getBuyerId();
        }
        return null;
    }
}
