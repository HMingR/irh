package top.imuster.goods.components;

import org.springframework.stereotype.Component;
import top.imuster.common.core.annotation.ReleaseAnnotation;
import top.imuster.common.core.enums.OperationType;
import top.imuster.common.core.enums.ReleaseType;
import top.imuster.goods.api.dto.ESProductDto;

/**
 * @ClassName: Trans2ES
 * @Description: Trans2ES
 * @author: hmr
 * @date: 2020/5/23 9:15
 */
@Component
public class Trans2ES {

    @ReleaseAnnotation(type = ReleaseType.GOODS, value = "#p0", operationType = OperationType.INSERT)
    public void save2ES(ESProductDto productInfo){

    }
}
