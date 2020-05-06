package top.imuster.message.provider.service;

import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.core.enums.OperationType;

/**
 * @ClassName: EsOperationService
 * @Description: EsOperationService
 * @author: hmr
 * @date: 2020/5/6 11:01
 */
public interface EsOperationService {
    void executeByOperationType(BaseDomain info, OperationType type);

    void add2ES(BaseDomain info);

    void deleteById(Long id);

    void updateById(BaseDomain info);
}
