package top.imuster.user.provider.component;

import org.springframework.stereotype.Component;
import top.imuster.common.core.annotation.ReleaseAnnotation;
import top.imuster.common.core.enums.OperationType;
import top.imuster.common.core.enums.ReleaseType;
import top.imuster.goods.api.dto.ESProductDto;
import top.imuster.life.api.dto.EsArticleDto;

/**
 * @ClassName: Release2ES
 * @Description: Release2ES
 * @author: hmr
 * @date: 2020/5/22 12:25
 */
@Component
public class Release2ES {

    @ReleaseAnnotation(type = ReleaseType.GOODS, value = "#p0", operationType = OperationType.INSERT)
    public void save2ES(ESProductDto productInfo){

    }

    @ReleaseAnnotation(type = ReleaseType.ARTICLE, value = "#p0", operationType = OperationType.INSERT)
    public void save2ES(EsArticleDto articleInfo){
    }
}
