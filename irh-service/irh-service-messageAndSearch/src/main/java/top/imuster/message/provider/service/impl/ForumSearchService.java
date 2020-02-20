package top.imuster.message.provider.service.impl;


import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.message.dto.ForumSearchParam;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: GoodsSearchService
 * @Description: 商品搜索
 * @author: hmr
 * @date: 2020/1/31 10:32
 */
@Service("forumSearchService")
public class ForumSearchService {

    @Value("${irh.forum.index}")
    private String forumIndex;

    @Value("${irh.forum.type}")
    private String forumType;

    @Value("${irh.forum.source_field}")
    private String forumSourceField;

    @Autowired
    RestHighLevelClient restHighLevelClient;


    public Message<Page<ArticleInfo>> forumList(int page, int size, ForumSearchParam searchParam){
        if(searchParam == null){
            searchParam = new ForumSearchParam();
        }
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest(forumIndex);
        //设置搜索类型
        searchRequest.types(forumType);

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //过虑源字段
        String[] source_field_array = forumSourceField.split(",");
        searchSourceBuilder.fetchSource(source_field_array,new String[]{});
        //创建布尔查询对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //搜索条件
        //根据关键字搜索
        if(StringUtils.isNotEmpty(searchParam.getKeyword())){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(searchParam.getKeyword(),  "content", "title")
                    .minimumShouldMatch("70%")
                    .field("name", 10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }
        if(StringUtils.isNotEmpty(searchParam.getMt())){
            //根据一级分类
            boolQueryBuilder.filter(QueryBuilders.termQuery("mt",searchParam.getMt()));
        }
        if(StringUtils.isNotEmpty(searchParam.getSt())){
            //根据二级分类
            boolQueryBuilder.filter(QueryBuilders.termQuery("st",searchParam.getSt()));
        }

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

        Message<Page<ArticleInfo>> queryResult = new Message<Page<ArticleInfo>>();
        Page<ArticleInfo> productInfoPage = new Page<>();
        List<ArticleInfo> list = productInfoPage.getResult();
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
                ArticleInfo info = new ArticleInfo();
                //源文档
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();

                //取出id
                String id = (String)sourceAsMap.get("id");
                info.setId(Long.parseLong(id));

                //取出title
                String name = (String) sourceAsMap.get("title");
                info.setTitle(name);

                //取出封面图片
                String mainPictureUrl = (String) sourceAsMap.get("main_picture_url");
                info.setMainPicture(mainPictureUrl);

                list.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        queryResult.setData(productInfoPage);
        return queryResult;
        
    }
}
