package top.imuster.life.api.service.hystrix;

import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.life.api.pojo.ArticleCategoryInfo;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.service.ForumServiceFeignApi;

/**
 * @ClassName: ForumServiceFeignHystrix
 * @Description: ForumServiceFeignHystrix
 * @author: hmr
 * @date: 2020/2/1 12:10
 */
@Component
public class ForumServiceFeignHystrix implements FallbackFactory<ForumServiceFeignApi>{

    private static final Logger log = LoggerFactory.getLogger(ForumServiceFeignHystrix.class);

    @Override
    public ForumServiceFeignApi create(Throwable throwable) {
        throwable.printStackTrace();
        return new ForumServiceFeignApi() {
            @Override
            public Message<String> adminDeleteArticle(Long id) {
                log.error("管理员根据id删除帖子失败,帖子id为{}", id);
                return null;
            }

            @Override
            public boolean adminDeleteArticleReview(Long id) {
                log.error("管理员根据留言id删除帖子留言失败,留言id为{}", id);
                return false;
            }

            @Override
            public Long getUserIdByType(Long targetId, Integer type) {
                log.error("管理员根据类型查看用户id失败");
                return null;
            }

            @Override
            public ArticleInfo getArticleInfoById(Long targetId) {
                return null;
            }
        };
    }
}
