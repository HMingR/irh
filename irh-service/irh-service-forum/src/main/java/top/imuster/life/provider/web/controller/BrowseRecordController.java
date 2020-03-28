package top.imuster.life.provider.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.NeedLogin;
import top.imuster.common.core.controller.BaseController;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.provider.service.BrowseRecordService;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @ClassName: BrowseRecordController
 * @Description: 论坛模块的浏览记录
 * @author: hmr
 * @date: 2020/3/28 10:06
 */
@RestController
@RequestMapping("/history")
public class BrowseRecordController extends BaseController {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Resource
    BrowseRecordService browseRecordService;


    @ApiOperation("查看浏览历史")
    @GetMapping("/{pageSize}/{currentPage}")
    @NeedLogin
    public Message<Page<ArticleInfo>> getRecordList(@PathVariable("pageSize") int pageSize, @PathVariable("currentPage") int currentPage) throws IOException {
        Long userId = getCurrentUserIdFromCookie();
        return browseRecordService.getRecordList(userId, pageSize, currentPage);
    }

}
