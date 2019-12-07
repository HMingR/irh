package top.imuster.user.service;

import top.imuster.service.base.BaseService;
import top.imuster.user.pojo.ConsumerInfo;

/**
 * ConsumerInfoService接口
 * @author 黄明人
 * @since 2019-11-26 10:46:26
 */
public interface ConsumerInfoService extends BaseService<ConsumerInfo, Long> {

    ConsumerInfo loginByName(String name);
}