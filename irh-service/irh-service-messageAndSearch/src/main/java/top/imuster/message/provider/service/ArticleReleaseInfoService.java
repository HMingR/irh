package top.imuster.message.provider.service;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;
import top.imuster.life.api.pojo.ArticleInfo;

/**
 * @ClassName: ArticleReleaseInfoService
 * @Description: ArticleReleaseInfoService
 * @author: hmr
 * @date: 2020/4/24 11:32
 */
@Service("articleReleaseInfoService")
public interface ArticleReleaseInfoService extends ElasticsearchRepository<ArticleInfo, String> {
}
