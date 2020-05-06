package imuster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.goods.api.dto.ESProductDto;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.message.provider.MessageProviderApplication;
import top.imuster.message.provider.dao.GoodsRepository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: EsTest
 * @Description: TODO
 * @author: hmr
 * @date: 2020/5/5 11:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MessageProviderApplication.class)
public class EsTest {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    private TransportClient transportClient;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchOperations elasticsearchOperations;


    private static final Logger log = LoggerFactory.getLogger(EsTest.class);

    public void test000(){
//        UpdateQuery updateQuery = QueryBuilders.termQuery("id", 10);
//        elasticsearchOperations.update()
    }


    @Test
    public void test() throws JsonProcessingException {

        ESProductDto esProductDto = new ESProductDto();
        esProductDto.setId(11L);
        esProductDto.setConsumerId(8L);
//        esProductDto.setSalePrice("1000");
        esProductDto.setTitle("想低价收一台山地自行车");
        esProductDto.setMainPicUrl("group1/M00/00/01/rBgYGV6wxzyAYZe_AAAi4sTh7gg602.png");
//        esProductDto.setTradeType(10);
        esProductDto.setDesc("马上毕业了，那位学长有自行车");
        esProductDto.setType(2);
        esProductDto.setTagNames("测试|自行车|毕业季");
        esProductDto.setCreateTime(DateUtil.current());
        Iterable<ESProductDto> id = goodsRepository.search(QueryBuilders.matchPhraseQuery("id", 10));
        //IndexResponse indexResponse = transportClient.prepareIndex("goods", "goods").setSource(objectMapper.writeValueAsString(esProductDto)).get();
        //System.out.println(indexResponse.getIndex());
    }


    @Test
    public void test02() throws IOException {
        SearchRequest searchRequest = new SearchRequest("goods");


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //过虑源字段
        String[] source_field_array = "id,title,mainPicUrl,salePrice,tradeType,desc,customerId,tagNames,type".split(",");
        searchSourceBuilder.fetchSource(source_field_array,new String[]{});
        //创建布尔查询对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //搜索条件
        //根据关键字搜索
        if(StringUtils.isNotEmpty("")){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("看看", "productName", "productDesc")
                    .minimumShouldMatch("70%")
                    .field("productName", 10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }


        RangeQueryBuilder rangequerybuilder = QueryBuilders
                .rangeQuery("salePrice")
                .from("500").to("1100");

        searchSourceBuilder.query(boolQueryBuilder);
        //searchSourceBuilder.query(rangequerybuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest);
        SearchHits hits = search.getHits();
        long totalHits = hits.totalHits;
        Message<Page<ESProductDto>> queryResult = new Message<Page<ESProductDto>>();
        Page<ProductInfo> productInfoPage = new Page<>();
        List<ProductInfo> list = productInfoPage.getData();
        productInfoPage.setTotalCount((int)totalHits);
        SearchHit[] searchHits = hits.getHits();
        for(SearchHit hit:searchHits){
            ESProductDto info = new ESProductDto();
            //源文档
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            log.info(objectMapper.writeValueAsString(sourceAsMap));
            //取出id
            String id = String.valueOf(sourceAsMap.get("id"));
            info.setId(Long.parseLong(id));

            //取出name
            String productName = (String) sourceAsMap.get("title");
            info.setTitle(productName);

            //图片
            String pic = (String) sourceAsMap.get("mainPicUrl");
            info.setMainPicUrl(pic);

            String desc = (String) sourceAsMap.get("desc");
            if(StringUtils.isNotEmpty(desc)) info.setDesc(desc);

            //价格
            String price = (String) sourceAsMap.get("salePrice");
            info.setSalePrice(price);

            /*String tradeType = String.valueOf(sourceAsMap.get("tradeType"));
            if(StringUtils.isNotEmpty(tradeType)){
                info.setTradeType(Integer.parseInt(tradeType));
            }*/


            String type = String.valueOf(sourceAsMap.get("type"));
            if(StringUtils.isNotEmpty(type)) info.setType(Integer.parseInt(type));

            //将coursePub对象放入list
            log.info(objectMapper.writeValueAsString(info));
        }
    }

    @Test
    public void test03() throws IOException {
        ESProductDto esProductDto = new ESProductDto();
        esProductDto.setTitle("想低价收一台自行车，想图片中的这样的");
        esProductDto.setMainPicUrl("group1/M00/00/02/rBgYGV6xbqqAfFF5AAAi4sTh7gg310.png");
        esProductDto.setDesc("马上毕业了，哪位学长有自行车");
        esProductDto.setType(2);
        esProductDto.setTagNames("测试|自行车|毕业季|更新");
        esProductDto.setCreateTime(DateUtil.current());
        transportClient.prepareIndex("goods", "goods");

        SearchRequest searchRequest = new SearchRequest("goods");
        SearchRequestBuilder srb = transportClient.prepareSearch("goods").setTypes("goods");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.fetchSource("_id", new String());
        searchRequest.source(searchSourceBuilder);

        QueryBuilder queryBuilder = QueryBuilders.matchPhraseQuery("id", "10L");
        QueryBuilder queryBuilder2 = QueryBuilders.matchPhraseQuery("type", "1");


        SearchResponse search = restHighLevelClient.search(searchRequest);
        SearchResponse sr = srb.setQuery(QueryBuilders.boolQuery()
                .must(queryBuilder)
                .must(queryBuilder2))
                .execute()
                .actionGet();
        SearchHits hits=sr.getHits();
        for(SearchHit hit:hits){
            System.out.println(hit.getSourceAsString());
        }

    }


/*
    public void test2(){
        try {
            Settings settings = Settings.builder().put("cluster.name", "my-application").build();
            TransportClient client = new PreBuiltTransportClient(settings)
                    .addTransportAddresses(new TransportAddress(InetAddress.getByName("39.97.121.108"), 9300L));
            System.out.println(client.toString());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }*/

    @Test
    public void highClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("39.97.121.108", 9300, "http")));
        System.out.println(client.toString());
    }

    /**
     * 创建索引
     *
     * @param index
     * @return
     */
    public boolean createIndex(String index) {
        if (!isIndexExist(index)) {
            log.info("Index is not exits!");
        }
        CreateIndexResponse indexresponse = transportClient.admin().indices().prepareCreate(index).execute().actionGet();
        log.info("执行建立成功？" + indexresponse.isAcknowledged());

        return indexresponse.isAcknowledged();
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public boolean deleteIndex(String index) {
        if (!isIndexExist(index)) {
            log.info("Index is not exits!");
        }
        DeleteIndexResponse dResponse = transportClient.admin().indices().prepareDelete(index).execute().actionGet();
        if (dResponse.isAcknowledged()) {
            log.info("delete index " + index + "  successfully!");
        } else {
            log.info("Fail to delete index " + index);
        }
        return dResponse.isAcknowledged();
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    public boolean isIndexExist(String index) {
        IndicesExistsResponse inExistsResponse = transportClient.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        if (inExistsResponse.isExists()) {
            log.info("Index [" + index + "] is exist!");
        } else {
            log.info("Index [" + index + "] is not exist!");
        }
        return inExistsResponse.isExists();
    }

}
