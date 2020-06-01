package top.imuster.life.provider.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.BrowseRecordDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.provider.service.ArticleInfoService;
import top.imuster.life.provider.service.BrowseRecordService;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: BrowseRecordServiceImpl
 * @Description: BrowseRecordServiceImpl
 * @author: hmr
 * @date: 2020/3/28 10:46
 */
@Service("browseRecordService")
public class BrowseRecordServiceImpl implements BrowseRecordService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Resource
    ArticleInfoService articleInfoService;

    public Message<Page<ArticleInfo>> getRecordList(Long userId, int pageSize, int currentPage) throws IOException {
        String browseRecordKey = RedisUtil.getBrowseRecordKey(BrowserType.FORUM, userId);
        List<String> list = redisTemplate.opsForList().range(browseRecordKey, (currentPage - 1) * pageSize, pageSize);
        if(list == null || list.isEmpty()){
            return Message.createBySuccess();
        }
        Page<ArticleInfo> page = new Page<>();
        ArrayList<ArticleInfo> res = new ArrayList<>();
        BrowseRecordDto browseRecordDto;
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        for (String a : list) {
            browseRecordDto = objectMapper.readValue(a, BrowseRecordDto.class);
            ArticleInfo articleInfo = articleInfoService.getBriefById(browseRecordDto.getTargetId());
            res.add(articleInfo);
        }
        page.setTotalCount(redisTemplate.opsForList().size(browseRecordKey).intValue());
        return Message.createBySuccess(page);
    }
}
