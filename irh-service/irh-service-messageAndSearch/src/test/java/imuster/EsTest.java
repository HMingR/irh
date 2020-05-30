package imuster;

import cn.hutool.core.bean.BeanUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.common.lucene.search.function.FiltersFunctionScoreQuery;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.RandomScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.WeightBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import top.imuster.common.base.domain.Page;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.goods.api.dto.ESProductDto;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.message.dto.GoodsSearchParam;
import top.imuster.message.provider.MessageProviderApplication;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.Security;
import java.util.*;

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
    private JavaMailSender javaMailSender;

    private static final Logger log = LoggerFactory.getLogger(EsTest.class);


    @Autowired
    Configuration configuration;

    @Test
    public void test0909() throws IOException {
        IndexRequest indexRequest = new IndexRequest("goods", "goods");
        ESProductDto esProductDto = new ESProductDto();
        esProductDto.setId(21L);
        esProductDto.setConsumerId(8L);
        esProductDto.setTitle("谁有华为Mate book14的，高价收");
        esProductDto.setMainPicUrl("group1/M00/00/01/rBgYGV6wxzyAYZe_AAAi4sTh7gg602.png");
        esProductDto.setDesc("马上毕业了，那位学长有华为两年前出的哪一款Mate book14,高价收");
        esProductDto.setType(2);
        esProductDto.setTagNames("测试|电脑|毕业季|apple");
        esProductDto.setCreateTime(DateUtil.current());
        String jwtString = objectMapper.writeValueAsString(esProductDto);
        indexRequest.source(jwtString, XContentType.JSON).id(esProductDto.getId().toString());
        restHighLevelClient.index(indexRequest);
    }

    @Test
    public void test02020() throws IOException {
        UpdateRequest updateRequest = new UpdateRequest("goods", "goods", "21");
        ESProductDto esProductDto = new ESProductDto();
        esProductDto.setDesc("马上毕业了，那位学长有华为两年前出的哪一款Mate book14,高价收，刚才打错字了");
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(esProductDto, true, true);
        updateRequest.doc(stringObjectMap);
        restHighLevelClient.update(updateRequest);
    }

    public void test0303(){
    }

    @Test
    public void sendTemplateMail() {

        MimeMessage mimeMailMessage = null;
        try {
            //template = configuration.getTemplate("Simple.ftl", "UTF-8");
            mimeMailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMailMessage, true);
            mimeMessageHelper.setFrom("irhmail@imuster.top", "irh");
            mimeMessageHelper.setTo("1978773465@qq.com");
            mimeMessageHelper.setSubject("irh平台验证码登录");

            Map<String, Object> model = new HashMap<>();
            model.put("context","25de");
            model.put("date", DateUtil.now());
            Template template = configuration.getTemplate("UserRegister.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            System.out.println(text);
            mimeMessageHelper.setText(text, true);

            javaMailSender.send(mimeMailMessage);
        } catch (Exception e) {
            log.error("邮件发送失败{}", e.getMessage());
        }

    }

    @Test
    public void test07(){
        try {
            //设置SSL连接、邮件环境
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", "smtp.mxhichina.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.setProperty("mail.smtp.auth", "true");
            //建立邮件会话
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                //身份认证
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("postmaster@imuster.top", "197877346Hmr");
                }
            });
            //建立邮件对象
            MimeMessage message = new MimeMessage(session);
            //设置邮件的发件人、收件人、主题
            //附带发件人名字
//            message.setFrom(new InternetAddress("from_mail@qq.com", "optional-personal"));
            message.setFrom(new InternetAddress("postmaster@imuster.top"));
            message.setRecipients(Message.RecipientType.TO, "1978773465@qq.com");
            message.setSubject("irh注册验证码");
            //文本
            String content="本次验证码为：1234a";
            message.setText(content);


            Map<String, Object> model = new HashMap<>();
            model.put("context","irh验证登录");
            model.put("date", DateUtil.now());
            Template template = configuration.getTemplate("Simple.ftl");
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
            System.out.println(text);
            message.setText(text);

            message.setSentDate(new Date());
            message.saveChanges();
            //发送邮件
            Transport.send(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void test() throws JsonProcessingException {
        ESProductDto esProductDto = new ESProductDto();
        esProductDto.setId(20L);
        esProductDto.setConsumerId(8L);
//        esProductDto.setSalePrice("1000");
        esProductDto.setTitle("谁有华为Mate book13的，高价收");
        esProductDto.setMainPicUrl("group1/M00/00/01/rBgYGV6wxzyAYZe_AAAi4sTh7gg602.png");
//        esProductDto.setTradeType(10);
        esProductDto.setDesc("马上毕业了，那位学长有华为两年前出的哪一款Mate book13,高价收收收收收收收收");
        esProductDto.setType(2);
        esProductDto.setTagNames("测试|电脑|毕业季|apple");
        esProductDto.setCreateTime(DateUtil.current());
        String jwtString = objectMapper.writeValueAsString(esProductDto);
        //Iterable<ESProductDto> id = goodsRepository.search(QueryBuilders.matchPhraseQuery("id", 10));
        IndexResponse indexResponse = transportClient.prepareIndex("goods", "goods").setSource(jwtString).setId("14").get();
        System.out.println(indexResponse.getIndex());
    }


    @Test
    public void test02() throws IOException {
        SearchRequest searchRequest = new SearchRequest("goods");


        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //过虑源字段
        String[] source_field_array = "id,title,mainPicUrl,tagNames,salePrice,tradeType,desc,type,createTime".split(",");
        searchSourceBuilder.fetchSource(source_field_array,new String[]{});


        //创建布尔查询对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //搜索条件
        //根据关键字搜索
        if(StringUtils.isNotEmpty("这是标题")){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery("这是标题", "title", "desc")
                    .minimumShouldMatch("70%")
                    .field("title", 10);
            boolQueryBuilder.must(multiMatchQueryBuilder);
        }


        searchSourceBuilder.query(boolQueryBuilder);
//        searchSourceBuilder.sort("salePrice", SortOrder.DESC);
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest);
        SearchHits hits = search.getHits();
        long totalHits = hits.totalHits;
        top.imuster.common.base.wrapper.Message<Page<ESProductDto>> queryResult = new top.imuster.common.base.wrapper.Message<Page<ESProductDto>>();
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
            /*String id = String.valueOf(sourceAsMap.get("id"));
            if(StringUtils.isNotBlank(id)){
                info.setId(Long.parseLong(id));

            }*/

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

            String type = String.valueOf(sourceAsMap.get("type"));
            if(StringUtils.isNotEmpty(type)) info.setType(Integer.parseInt(type));

            //将coursePub对象放入list
            log.info(objectMapper.writeValueAsString(info));
        }
    }

    @Test
    public void test009(){
        GoodsSearchParam searchParam = new GoodsSearchParam();
        searchParam.setKeyword("这是标题");
        if(searchParam == null){
            searchParam = new GoodsSearchParam();
        }
        //创建搜索请求对象
        SearchRequest searchRequest = new SearchRequest("goods");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //过虑源字段
        String[] source_field_array = "id,title,mainPicUrl,tagNames,salePrice,tradeType,desc,type,createTime".split(",");

        searchSourceBuilder.fetchSource(source_field_array,new String[]{});
        //创建布尔查询对象
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //搜索条件
        //根据关键字搜索
        if(StringUtils.isNotEmpty(searchParam.getKeyword())){
            MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(searchParam.getKeyword(), "title", "desc", "tagNames")
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
        SortOrder orderType = null;
        if(priceOrder != null){
            if(priceOrder == 1){
                orderType = SortOrder.DESC;
            }else if(priceOrder == 2){
                orderType = SortOrder.ASC;
            }else{
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
            }
            searchSourceBuilder.sort("createTime", orderType);
        }


        searchSourceBuilder.query(rangequerybuilder);
        searchSourceBuilder.query(boolQueryBuilder);


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
                log.info(objectMapper.writeValueAsString(info));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        productInfoPage.setData(list);

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


    @Test
    public void test09(){
        String searchContent = "手机";
        String index = "goods";
        SearchRequestBuilder searchBuilder = transportClient.prepareSearch(index);
        //分页
        searchBuilder.setFrom(0).setSize(10);
        //explain为true表示根据数据相关度排序，和关键字匹配最高的排在前面
        searchBuilder.setExplain(true);

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        // 搜索 title字段包含IPhone的数据
        queryBuilder.must(QueryBuilders.matchQuery("title", searchContent));

        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[2];

        //过滤条件1：分类为：品牌手机最重要 -- 权重查询Weight
        ScoreFunctionBuilder<WeightBuilder> scoreFunctionBuilder = new WeightBuilder();
        scoreFunctionBuilder.setWeight(2);
        QueryBuilder termQuery = QueryBuilders.termQuery("title", "iphone");
        FunctionScoreQueryBuilder.FilterFunctionBuilder category = new FunctionScoreQueryBuilder.FilterFunctionBuilder(termQuery, scoreFunctionBuilder);
        filterFunctionBuilders[0] = category;


        // 过滤条件2：销量越高越排前 --计分查询 FieldValueFactor
//        ScoreFunctionBuilder<FieldValueFactorFunctionBuilder> fieldValueScoreFunction = new FieldValueFactorFunctionBuilder("createTime");
//        ((FieldValueFactorFunctionBuilder) fieldValueScoreFunction).factor(1.2f);
//        FunctionScoreQueryBuilder.FilterFunctionBuilder sales = new FunctionScoreQueryBuilder.FilterFunctionBuilder(fieldValueScoreFunction);
//        filterFunctionBuilders[1] = sales;

        // 给定每个用户随机展示：  --random_score
        ScoreFunctionBuilder<RandomScoreFunctionBuilder> randomScoreFilter = new RandomScoreFunctionBuilder();
        ((RandomScoreFunctionBuilder) randomScoreFilter).seed(2);
        FunctionScoreQueryBuilder.FilterFunctionBuilder random = new FunctionScoreQueryBuilder.FilterFunctionBuilder(randomScoreFilter);
        filterFunctionBuilders[1] = random;

        // 多条件查询 FunctionScore
        FunctionScoreQueryBuilder query = QueryBuilders.functionScoreQuery(queryBuilder, filterFunctionBuilders)
                .scoreMode(FiltersFunctionScoreQuery.ScoreMode.SUM).boostMode(CombineFunction.SUM);

        SortBuilder sortBuilder1 = SortBuilders.fieldSort("createTime").order(SortOrder.DESC);

        searchBuilder.setQuery(query);

        SearchResponse response = searchBuilder.execute().actionGet();
        SearchHits hits = response.getHits();
        String searchSource;
        for (SearchHit hit : hits)
        {
            searchSource = hit.getSourceAsString();
            System.out.println(searchSource);
        }
        //        long took = response.getTook().getMillis();
        long total = hits.getTotalHits();
        System.out.println(total);

    }

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

    @Test
    public void test05(){
        try {
            //设置SSL连接、邮件环境
            Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
            Properties props = System.getProperties();
            props.setProperty("mail.smtp.host", "smtp.mxhichina.com");
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");
            props.setProperty("mail.smtp.auth", "true");
            //建立邮件会话
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                //身份认证
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("postmaster@imuster.top", "197877346Hmr");
                }
            });
            //建立邮件对象
            MimeMessage message = new MimeMessage(session);
            //附带发件人名字
//
           message.setFrom(new InternetAddress("postmaster@imuster.top", "optional-personal"));
            message.setFrom(new InternetAddress("postmaster@imuster.top"));
            message.setRecipients(Message.RecipientType.TO, " 1978773465@qq.com");
            message.setSubject("irh平台注册验证码");
            //文本
            String content="本次验证码为2342，请马上填写";
            message.setText(content);
            message.setSentDate(new Date());
            message.saveChanges();
            //发送邮件
            Transport.send(message);
        } catch (Exception e) {
            System.out.println(e.toString());
        }


    }

}
