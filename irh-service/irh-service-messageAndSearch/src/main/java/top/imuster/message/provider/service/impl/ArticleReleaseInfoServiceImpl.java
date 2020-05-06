package top.imuster.message.provider.service.impl;

import org.springframework.stereotype.Service;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.enums.OperationType;
import top.imuster.message.provider.service.ArticleReleaseInfoService;

/**
 * @ClassName: ArticleReleaseInfoServiceImpl
 * @Description: ArticleReleaseInfoServiceImpl
 * @author: hmr
 * @date: 2020/5/6 12:14
 */
@Service("articleReleaseInfoService")
public class ArticleReleaseInfoServiceImpl implements ArticleReleaseInfoService {

    @Override
    public void executeByOperationType(BaseDomain info, OperationType type) {
        if(OperationType.INSERT.equals(type)) add2ES(info);
    }

    @Override
    public void add2ES(BaseDomain info) {

    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void updateById(BaseDomain info) {

    }


}
