package top.imuster.message.provider.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.dto.ESProductDto;
import top.imuster.message.dto.GoodsSearchParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: GoodsSearchService
 * @Description: 商品搜索
 * @author: hmr
 * @date: 2020/1/31 10:32
 */
@Service("searchService")
public class GoodsSearchService {

    @Value("${irh.goods.index}")
    private String goodsIndex;

    @Value("${irh.goods.type}")
    private String goodsType;

    @Value("${irh.goods.source_field}")
    private String goodsSourceField;

    @Autowired
    RestHighLevelClient restHighLevelClient;


    public Message<Page<ESProductDto>> goodsList(int page, int size, GoodsSearchParam searchParam){
        if(searchParam == null){
            searchParam = new GoodsSearchParam();
        }
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest(goodsIndex);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //过虑源字段
        String[] source_field_array = goodsSourceField.split(",");
        searchSourceBuilder.fetchSource(source_field_array,new String[]{});
        //创建布尔查询对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //搜索条件
        //根据关键字搜索
        if(StringUtils.isNotEmpty(searchParam.getKeyword())){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(searchParam.getKeyword(), "title", "desc", "tagNames", "content")
                    .minimumShouldMatch("70%")
                    .field("title", 10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }

        //交易类型
        if(searchParam.getTradeType() != null){
            boolQueryBuilder.filter(QueryBuilders.termQuery("tradeType",searchParam.getTradeType()));
        }

        RangeQueryBuilder rangequerybuilder = null;
        if(StringUtils.isNotEmpty(searchParam.getPriceMin()) && StringUtils.isNotEmpty(searchParam.getPriceMax())){
            rangequerybuilder = QueryBuilders
                    .rangeQuery("salePrice")
                    .from(searchParam.getPriceMin()).to(searchParam.getPriceMax());
        }else if(StringUtils.isNotEmpty(searchParam.getPriceMin()) && StringUtils.isEmpty(searchParam.getPriceMax())){
            rangequerybuilder = QueryBuilders
                    .rangeQuery("salePrice")
                    .from(searchParam.getPriceMin());
        }else if(StringUtils.isEmpty(searchParam.getPriceMin()) && StringUtils.isNotEmpty(searchParam.getPriceMax())){
            rangequerybuilder = QueryBuilders
                    .rangeQuery("salePrice")
                    .to(searchParam.getPriceMax());
        }

        Integer priceOrder = searchParam.getPriceOrder();
        SortOrder orderType;
        if(priceOrder != null){
            if(priceOrder == 1){
                orderType = SortOrder.DESC;
            }else if(priceOrder == 2){
                orderType = SortOrder.ASC;
            }else{
                return Message.createByError("参数错误");
            }
            searchSourceBuilder.sort("salePrice", orderType);
        }

        Integer timeOrder = searchParam.getTimeOrder();
        if(timeOrder != null){
            if(timeOrder == 1){
                orderType = SortOrder.DESC;
            }else if(timeOrder == 2){
                orderType = SortOrder.ASC;
            }else{
                return Message.createByError("参数错误");
            }
            searchSourceBuilder.sort("createTime", orderType);
        }


        searchSourceBuilder.query(rangequerybuilder);
        searchSourceBuilder.query(boolQueryBuilder);

        //设置分页参数
        if(page<=0){
            page = 1;
        }
        if(size<=0){
            size = 12;
        }
        //起始记录下标
        int from  = (page-1)*size;
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);

        searchRequest.source(searchSourceBuilder);

        Page<ESProductDto> productInfoPage = new Page<>();
        List<ESProductDto> list = new ArrayList<>();
        try {
            //执行搜索
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest);
            //获取响应结果
            SearchHits hits = searchResponse.getHits();
            //匹配的总记录数
            long totalHits = hits.totalHits;
            productInfoPage.setTotalCount((int)totalHits);
            SearchHit[] searchHits = hits.getHits();
            for(SearchHit hit:searchHits){
                ESProductDto info = new ESProductDto();
                //源文档
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                //取出id
                Object id =sourceAsMap.get("id");

                if(id != null){
                    info.setId(Long.parseLong(String.valueOf(id)));

                }

                //取出name
                String title = (String) sourceAsMap.get("title");
                info.setTitle(title);

                //图片
                String pic = (String) sourceAsMap.get("mainPicUrl");
                info.setMainPicUrl(pic);

                //价格
                Object salePrice = sourceAsMap.get("salePrice");
                if(salePrice != null){
                    info.setSalePrice(String.valueOf(salePrice));
                }

                Object tagNames = sourceAsMap.get("tagNames");
                if(tagNames != null) info.setTagNames(String.valueOf(tagNames));

                //trade_type
                Object tradeType = sourceAsMap.get("tradeType");
                if(tradeType != null){
                    info.setTradeType(Integer.parseInt(String.valueOf(tradeType)));
                }

                Object type = sourceAsMap.get("type");
                if(type != null){
                    info.setType(Integer.parseInt(String.valueOf(type)));
                }

                Object desc = sourceAsMap.get("desc");
                if(desc != null) info.setDesc(String.valueOf(desc));
                //将coursePub对象放入list
                list.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        productInfoPage.setData(list);
        return Message.createBySuccess(productInfoPage);
        
    }
}
