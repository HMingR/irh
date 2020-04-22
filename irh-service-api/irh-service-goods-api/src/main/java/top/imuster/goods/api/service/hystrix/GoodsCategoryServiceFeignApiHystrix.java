package top.imuster.goods.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductCategoryInfo;
import top.imuster.goods.api.service.GoodsCategoryServiceFeignApi;

import java.util.List;

/**
 * @ClassName: GoodsCategoryServiceFeignApiHystrix
 * @Description: GoodsCategoryServiceFeignApiHystrix
 * @author: hmr
 * @date: 2020/2/7 17:08
 */
@Component
public class GoodsCategoryServiceFeignApiHystrix implements FallbackFactory<GoodsCategoryServiceFeignApi> {

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public GoodsCategoryServiceFeignApi create(Throwable throwable) {
        return new GoodsCategoryServiceFeignApi() {
            @Override
            public Message<List<ProductCategoryInfo>> adminCategoryTree() {
                log.error("远程调用goods模块生成分类树失败");
                return null;
            }

            @Override
            public Message<String> addCategory(ProductCategoryInfo productCategoryInfo) {
                log.error("远程调用goods模块添加分类失败,分类信息为{}", productCategoryInfo);
                return null;
            }

            @Override
            public Message<String> delCategory(Long id) {
                log.error("远程调用goods模块根据id删除分类失败,id为{}", id);
                return null;
            }

            @Override
            public Message<ProductCategoryInfo> getInfoById(Long id) {
                log.error("远程调用goods模块根据id获得分类信息失败,id为{}", id);
                return null;
            }

            @Override
            public Message<String> editCategory(ProductCategoryInfo productCategoryInfo) {
                log.error("远程调用goods模块修改分类信息失败");
                return null;
            }
        };
    }
}
