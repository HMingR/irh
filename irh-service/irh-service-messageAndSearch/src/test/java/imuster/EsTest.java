package imuster;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.message.provider.MessageProviderApplication;

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

    private static final Logger log = LoggerFactory.getLogger(EsTest.class);


    @Test
    public void test() throws JsonProcessingException {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(10L);
        productInfo.setBrowserTimes(200L);
        productInfo.setConsumerId(5L);
        productInfo.setSalePrice("1000");
        productInfo.setCategoryId(1L);
        productInfo.setTitle("测试");
        productInfo.setMainPicUrl("group1/M00/00/01/rBgYGV6wxzyAYZe_AAAi4sTh7gg602.png");
        productInfo.setTradeType(10);
        productInfo.setProductDesc("我就看看es能不能用");
        productInfo.setOldDegree(9);
//        createIndex("goods");
        IndexResponse indexResponse = transportClient.prepareIndex("goods", "goods", "1").setSource(objectMapper.writeValueAsString(productInfo)).get();
        System.out.println(indexResponse.getIndex());
    }


    @Test
    public void test02() throws IOException {
        SearchRequest searchRequest = new SearchRequest("goods");
        SearchResponse search = restHighLevelClient.search(searchRequest);
        SearchHits hits = search.getHits();
        long totalHits = hits.totalHits;
        Message<Page<ProductInfo>> queryResult = new Message<Page<ProductInfo>>();
        Page<ProductInfo> productInfoPage = new Page<>();
        List<ProductInfo> list = productInfoPage.getData();
        productInfoPage.setTotalCount((int)totalHits);
        SearchHit[] searchHits = hits.getHits();
        for(SearchHit hit:searchHits){
            ProductInfo info = new ProductInfo();
            //源文档
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            log.info(objectMapper.writeValueAsString(sourceAsMap));
            //取出id
            String id = String.valueOf(sourceAsMap.get("id"));
            info.setId(Long.parseLong(id));

            //取出name
            String productName = (String) sourceAsMap.get("productName");
            info.setTitle(productName);

            //图片
            String pic = (String) sourceAsMap.get("mainPicUrl");
            info.setMainPicUrl(pic);

            //价格
            String price = (String) sourceAsMap.get("salePrice");
            info.setSalePrice(price);

            //trade_type
            String tradeType = String.valueOf(sourceAsMap.get("tradeType"));
            if(tradeType != null){
                info.setTradeType(Integer.parseInt(tradeType));
            }

            //旧价格
            if(sourceAsMap.get("originalPrice")!=null ) {
                String originalPrice = (String) sourceAsMap.get("originalPrice");
                info.setOriginalPrice(originalPrice);
            }
            //将coursePub对象放入list
            log.info(objectMapper.writeValueAsString(info));
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
