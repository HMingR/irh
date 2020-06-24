package top.imuster.goods.web.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.pojo.ProductMessageInfo;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.goods.exception.GoodsException;
import top.imuster.goods.service.ErrandInfoService;
import top.imuster.goods.service.ProductInfoService;
import top.imuster.goods.service.ProductMessageService;
import top.imuster.life.api.pojo.ErrandInfo;

import javax.annotation.Resource;

/**
 * @ClassName: GoodsServiceFeignClient
 * @Description: 商品模块提供的接口实现类
 * @author: hmr
 * @date: 2019/12/26 20:41
 */
@RestController
@RequestMapping("/goods/feign")
public class GoodsServiceFeignClient implements GoodsServiceFeignApi {

    private static final Logger log = LoggerFactory.getLogger(GoodsServiceFeignClient.class);

    @Resource
    ProductInfoService productInfoService;

    @Resource
    ProductMessageService productMessageService;


    @Resource
    ErrandInfoService errandInfoService;

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
    @GetMapping("/es/lockStock/{productId}/{productVersion}")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProductInfo lockStock(@PathVariable("productId") Long productId, @PathVariable("productVersion") Integer version) throws GoodsException {
        try{
            Integer count = productInfoService.lockProduct(productId, version);
            if(count == null || count == 0) return null;
            ProductInfo productInfo = productInfoService.selectEntryList(productId).get(0);
            return productInfo;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    @DeleteMapping("/es/pm/{id}")
    public boolean deleteProductMessageById(@PathVariable("id") Long id) {
        ProductMessageInfo condition = new ProductMessageInfo();
        condition.setId(id);
        condition.setState(1);
        int i = productMessageService.updateByKey(condition);
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
            ProductMessageInfo productMessageInfo = productMessageService.selectEntryList(id).get(0);
            return productMessageInfo.getConsumerId();
        }
        return null;
    }

    @Override
    @GetMapping("/errand/{id}/{version}/{state}")
    public boolean updateErrandInfoById(@PathVariable("id") Long id, @PathVariable("version") Integer errandVersion, @PathVariable("state") Integer state) {
        return errandInfoService.updateStateByIdAndVersion(id, errandVersion, state);
    }

    @Override
    @GetMapping("/errand/{id}/{state}")
    public boolean updateErrandInfoById(@PathVariable("id") Long id, @PathVariable("state") Integer state) {
        return errandInfoService.updateStateById(id, state);
    }

    @Override
    @GetMapping("/errand/avail/{errandId}/{version}")
    public boolean errandIsAvailable(@PathVariable("errandId") Long errandId,@PathVariable("version") Integer errandVersion) {
        return errandInfoService.isAvailable(errandId, errandVersion);
    }

    @Override
    @GetMapping("/errand/addAndPhone/{id}")
    public ErrandInfo getErrandInfoById(@PathVariable("id") Long errandId) {
        return errandInfoService.getAddAndPhoneById(errandId);
    }

    @Override
    @GetMapping("/es/brief/{id}")
    public ProductInfo getProductBriefInfoById(@PathVariable("id") Long productId) {
        return productInfoService.getProductBriefInfoById(productId);
    }

    @Override
    @GetMapping("/es/state/{targetId}/{state}")
    public boolean updateProductState(@PathVariable("targetId") Long productId, @PathVariable("state") Integer state) {
        return productInfoService.updateProductStateById(productId, state);
    }

    @Override
    @GetMapping("/errand/finishPay/{id}")
    public boolean finishErrandPay(@PathVariable("id") Long id) {
        ErrandInfo errandInfo = new ErrandInfo();
        errandInfo.setId(id);
        errandInfo.setPayState(1);
        int i = errandInfoService.updateByKey(errandInfo);
        return i == 1;
    }
}
