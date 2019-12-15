package top.imuster.item.controller;

//import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.cloud.openfeign.FeignClient;

/**
 * @ClassName: ProductControllerApi
 * @Description: ProductController对外提供的接口
 * @author: hmr
 * @date: 2019/12/1 14:58
 */
@FeignClient(value = "item-provider")
public interface ProductControllerApi {
}
