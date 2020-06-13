package top.imuster.life.provider.service.impl;


import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HtmlUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.BrowserTimesDto;
import top.imuster.common.core.dto.rabbitMq.SendDetailPageDto;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.enums.TemplateEnum;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.life.api.dto.ForwardDto;
import top.imuster.life.api.dto.UserBriefDto;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.pojo.ForumHotTopicInfo;
import top.imuster.life.provider.dao.ArticleInfoDao;
import top.imuster.life.provider.service.ArticleCollectionService;
import top.imuster.life.provider.service.ArticleInfoService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * ArticleInfoService 实现类
 * @author 黄明人
 * @since 2020-01-30 15:25:20
 */
@Service("articleInfoService")
public class ArticleInfoServiceImpl extends BaseServiceImpl<ArticleInfo, Long> implements ArticleInfoService {

    protected  final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${batch.size}")
    private int batchSize;   //批量处理浏览记录的大小

    @Resource
    private ArticleInfoDao articleInfoDao;

    @Resource
    private ArticleCollectionService articleCollectionService;

    private static final String templateLocation = "static/article.ftl";

    @Autowired
    private Configuration configuration;
    
    private Template template;

    //文章模块的排名总数
    @Value("${article.rank.total}")
    private Long rankTotal;

    @Resource
    GenerateSendMessageService generateSendMessageService;

    @PostConstruct
    public void createTemplate() throws IOException {
        template = configuration.getTemplate(templateLocation, "UTF-8");
    }

    @Override
    public BaseDao<ArticleInfo, Long> getDao() {
        return this.articleInfoDao;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void release(Long userId, ArticleInfo articleInfo) {
        articleInfo.setState(3);
        articleInfo.setUserId(userId);
        String content = articleInfo.getContent();

        //清除文章中的html代码和空白字符
        String summaryText = StrUtil.cleanBlank(HtmlUtil.cleanHtmlTag(content));
        if(summaryText.length() > 50){
            articleInfo.setArticleSummary(summaryText.substring(0, 50));
        }else{
            articleInfo.setArticleSummary(summaryText);
        }
        Long articleId = articleInfoDao.insertArticle(articleInfo);
        createStaticPage(content, articleId);
    }

    /**
     * @Author hmr
     * @Description 根据前端用户提交的文章内容生成静态页面并保存到FASTDFS中，返回地址
     * @Date: 2020/4/8 8:30
     * @param context
     * @reture: java.lang.String
     **/
    private void createStaticPage(String context, Long articleId){
        SendDetailPageDto detailPageDto = new SendDetailPageDto();
        detailPageDto.setTemplateEnum(TemplateEnum.ARTICLE_TEMPLATE);
        detailPageDto.setEntry(context);
        detailPageDto.setTargetId(articleId);
        generateSendMessageService.sendToMq(detailPageDto);
    }

    @Override
    public Page<ArticleInfo> list(Page<ArticleInfo> page, Long userId) {
        if(page.getSearchCondition() == null){
            ArticleInfo condition = new ArticleInfo();
            condition.setOrderField("create_time");
            condition.setOrderFieldType("DESC");
            page.setSearchCondition(condition);
        }
        ArticleInfo searchCondition = page.getSearchCondition();
        int pageSize = page.getPageSize();
        int currentPage = page.getCurrentPage();
        searchCondition = page.getSearchCondition();

        if(userId != null) searchCondition.setUserId(userId);

        searchCondition.setState(2);
        searchCondition.setStartIndex((currentPage - 1) * pageSize);
        searchCondition.setEndIndex(pageSize);
        page.setTotalCount(articleInfoDao.selectEntryListCount(searchCondition));
        List<ArticleInfo> articleInfoList = articleInfoDao.selectListByCondition(page.getSearchCondition());
        page.setData(articleInfoList);
        return page;
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "'article::detail::' + #p0")
    public ArticleInfo getArticleDetailById(Long id) {
        List<ArticleInfo> articleInfoList = articleInfoDao.selectEntryList(id);
        if(CollectionUtils.isEmpty(articleInfoList)) return null;
        return articleInfoList.get(0);
    }

    @Override
    public Long getUserIdByArticleId(Long targetId) {
        ArticleInfo condition = new ArticleInfo();
        condition.setId(targetId);
        condition.setState(2);
        ArticleInfo articleInfo = articleInfoDao.selectListByCondition(condition).get(0);
        return articleInfo.getUserId();
    }

    @Override
    public List<ArticleInfo> getUpTotalByIds(Long[] ids) {
        return articleInfoDao.selectUpTotalByIds(ids);
    }

    @Override
    public Long getUpTotal(Long id) {
        return articleInfoDao.selectUpTotalById(id);
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "'article::brief::' + #p0")
    public ArticleInfo getBriefById(Long id) {
        return articleInfoDao.selectBriefById(id);
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "#p0+'::top::five'")
    public List<ArticleInfo> hotTopicListByCategory(Long id) {
        return articleInfoDao.selectUpTop5ByCategoryId(id);
    }

    @Override
    public List<ArticleInfo> selectInfoByTargetIds(Long[] longs) {
        return articleInfoDao.selectInfoByTargetIds(longs);
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_HOT_TOPIC_CACHE_KEY, key = "#p0")
    public ForumHotTopicInfo getBriefByHotTopicId(Long aLong) {
        return articleInfoDao.selectBriefByHotTopicId(aLong);
    }

    @Override
    public UserBriefDto getUserBriefByUserId(Long userId) {
        Long collectTotal = articleCollectionService.getCollectTotalByUserId(userId);
        UserBriefDto userBriefDto = articleInfoDao.selectUserBriefTotalById(userId);
        if(userBriefDto == null) userBriefDto = new UserBriefDto();
        userBriefDto.setCollectTotal(collectTotal);
        return userBriefDto;
    }

    @Override
    public void updateBrowserTimesFromRedis2Redis(List<BrowserTimesDto> res) {
        if(CollectionUtils.isEmpty(res)) return;
        Integer integer = articleInfoDao.updateBrowserTimesByCondition(res);
        log.info("一共更新了{}条记录的浏览总数", integer);
        /*if(res == null || res.isEmpty()) return;
        HashSet<Long> targetIds = new HashSet<>();
        HashSet<Long> times = new HashSet<>();
        res.stream().forEach(browserTimesDto -> {
            Long targetId = browserTimesDto.getTargetId();
            Long total = browserTimesDto.getTotal();
            targetIds.add(targetId);
            times.add(total);
        });
        Long[] ids = targetIds.toArray(new Long[targetIds.size()]);
        Long[] totals = times.toArray(new Long[times.size()]);
        for (int i = 0; i <= ids.length / batchSize; i++){
            ArrayList<ArticleInfo> update = new ArrayList<>();
            Long[] selectIds = new Long[batchSize];
            for (int j = i * batchSize, x = 0; j < (i + 1) * batchSize; j++, x++) {
                selectIds[x] = ids[j];
                if(j == ids.length - 1) break;
            }
           // Map<Long, Long> result = articleInfoDao.selectBrowserTimesByIds(ids);
            for (int z = 0; z < selectIds.length; z++){
                if(selectIds[z] == null || selectIds[z] == 0) break;
                Long selectId = selectIds[z];
                Long total = totals[i * batchSize + z];
                //Long original = result.get(selectId);
                //original = original + total;
                ArticleInfo condition = new ArticleInfo();
                condition.setId(selectId);
                condition.setBrowserTimes(total);
                update.add(condition);
            }
            articleInfoDao.updateBrowserTimesByCondition(update);
        }*/
    }

    @Override
    public void updateForwardTimesFromRedis2DB(List<ForwardDto> res) {
        articleInfoDao.updateForwardTimesByCondition(res);
    }

    @Override
    public Message<List<ArticleInfo>> getBriefByCategoryId(Long categoryId, Integer pageSize, Integer currentPage) {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setState(2);
        articleInfo.setCategoryId(categoryId);
        articleInfo.setStartIndex( (currentPage < 1? 1 : currentPage) * pageSize);
        articleInfo.setEndIndex(pageSize);
        //根据文章id获得文章的简略信息
        List<ArticleInfo> articleInfoList = articleInfoDao.selectArticleBriefByCategoryId(articleInfo);
        return Message.createBySuccess(articleInfoList);
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_ARTICLE_USER_RANK_CACHE_KEY, key = "#p1 + 'pageNumArticleRank'")
    public List<Long> getUserArticleRank(Integer pageSize, Integer currentPage) {
        HashMap<String, Integer> param = new HashMap<>();
        param.put("pageSize", pageSize);
        param.put("startIndex", (currentPage - 1) * pageSize);
        return articleInfoDao.selectUserArticleRank(param);
    }

    @Override
    @CacheEvict(value = GlobalConstant.IRH_COMMON_CACHE_KEY, key = "'article::brief::' + #p0", allEntries = true)
    public Message<String> adminDeleteArticle(Long id) {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setState(3);
        articleInfo.setId(id);
        articleInfoDao.updateByKey(articleInfo);

        ArticleInfo articleInfo1 = articleInfoDao.selectBriefById(id);

        SendUserCenterDto sendUserCenterDto = new SendUserCenterDto();
        sendUserCenterDto.setFromId(-1L);
        sendUserCenterDto.setDate(DateUtil.now());
        sendUserCenterDto.setContent(new StringBuffer().append("您的文章被多人举报，经过管理员审核，发现情况属实，现已屏蔽您的标题为《 ").append(articleInfo1.getTitle()).append(" 》的文章，请修改之后重新发布").toString());
        sendUserCenterDto.setToId(articleInfo.getUserId());
        sendUserCenterDto.setNewsType(70);

        generateSendMessageService.sendToMq(sendUserCenterDto);
        return Message.createBySuccess();
    }

    @Override
    public Message<String> deleteArticle(Long id, Long currentUserIdFromCookie) {
        ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setUserId(currentUserIdFromCookie);
        articleInfo.setState(1);
        int i = articleInfoDao.updateByKey(articleInfo);
        return i == 1 ? Message.createBySuccess() : Message.createByError();
    }
}