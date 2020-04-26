package top.imuster.goods.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.service.ProductRecordService;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName: ProductRecordController
 * @Description: 商品浏览记录查询
 * @author: hmr
 * @date: 2020/4/26 15:55
 */
@RestController
@RequestMapping("/record")
public class ProductRecordController extends BaseController {

    @Resource
    ProductRecordService productRecordService;

    /**
     * @Author hmr
     * @Description 用户查看自己的浏览记录
     * @Date: 2020/4/26 16:01
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    @NeedLogin
    @GetMapping("/{pageSize}/{currentPage}")
    public Message<Page<ProductInfo>> recordList(@PathVariable("pageSize") Integer pageSize, @PathVariable("currentPage") Integer currentPage) throws IOException {
        return productRecordService.getUserRecordList(pageSize, currentPage, getCurrentUserIdFromCookie());
    }
}
