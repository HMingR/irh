package top.imuster.goods.service;

import top.imuster.goods.api.dto.GoodsForwardDto;

import java.util.List;

/**
 * @ClassName: RedisOperateService
 * @Description: RedisOperateService
 * @author: hmr
 * @date: 2020/5/9 9:16
 */
public interface RedisOperateService{

    /**
     * @Author hmr
     * @Description 将商品的收藏记录保存到redis中
     * @Date: 2020/5/9 9:17
     * @param
     * @reture: void
     **/
    List<GoodsForwardDto> transCollect2DB(Integer type);
}
