package top.imuster.life.provider.service;

import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleInfo;

import java.io.IOException;

/**
 * @ClassName: BrowseRecordService
 * @Description: BrowseRecordService
 * @author: hmr
 * @date: 2020/3/28 10:46
 */
public interface BrowseRecordService {

    /**
     * @Author hmr
     * @Description 根据用户编号获得用户浏览记录
     * @Date: 2020/3/28 15:37
     * @param userId
     * @param pageSize
     * @param currentPage
     * @reture: top.imuster.common.base.wrapper.Message<java.util.List<top.imuster.life.api.pojo.ArticleInfo>>
     **/
    Message<Page<ArticleInfo>> getRecordList(Long userId, int pageSize, int currentPage) throws IOException;
}
