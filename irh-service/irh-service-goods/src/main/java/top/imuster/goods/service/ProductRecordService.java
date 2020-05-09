package top.imuster.goods.service;

import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;

import java.io.IOException;

/**
 * @ClassName: ProductRecordService
 * @Description: ProductRecordService
 * @author: hmr
 * @date: 2020/4/26 16:04
 */
public interface ProductRecordService {

    /**
     * @Author hmr
     * @Description
     * @Date: 2020/4/26 16:07
     * @param pageSize
     * @param currentPage
     * @param userId
     * @reture: top.imuster.common.base.wrapper.Message<top.imuster.common.base.domain.Page<top.imuster.goods.api.pojo.ProductInfo>>
     **/
    Message<Page<ProductInfo>> getUserRecordList(Integer pageSize, Integer currentPage, Long userId) throws IOException;

    /**
     * @Author hmr
     * @Description 删除所有
     * @Date: 2020/5/9 9:53
     * @param currentUserIdFromCookie
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> deleteAll(Long currentUserIdFromCookie);

    /**
     * @Author hmr
     * @Description 按照序号删除
     * @Date: 2020/5/9 10:30
     * @param index
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> deleteByIndex(Integer index, Long userId);
}
