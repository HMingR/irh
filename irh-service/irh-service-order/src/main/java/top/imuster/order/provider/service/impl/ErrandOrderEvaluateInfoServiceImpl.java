package top.imuster.order.provider.service.impl;


import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import top.imuster.common.base.config.GlobalConstant;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.order.api.pojo.ErrandOrderEvaluateInfo;
import top.imuster.order.api.pojo.ErrandOrderInfo;
import top.imuster.order.provider.dao.ErrandOrderEvaluateInfoDao;
import top.imuster.order.provider.service.ErrandOrderEvaluateInfoService;
import top.imuster.order.provider.service.ErrandOrderService;

import javax.annotation.Resource;
import java.util.List;

/**
 * ErrandOrderEvaluateInfoService 实现类
 * @author 黄明人
 * @since 2020-06-19 19:54:16
 */
@Service("errandOrderEvaluateInfoService")
public class ErrandOrderEvaluateInfoServiceImpl extends BaseServiceImpl<ErrandOrderEvaluateInfo, Long> implements ErrandOrderEvaluateInfoService {

    @Resource
    private ErrandOrderEvaluateInfoDao errandOrderEvaluateInfoDao;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Resource
    private ErrandOrderService errandOrderService;

    @Override
    public BaseDao<ErrandOrderEvaluateInfo, Long> getDao() {
        return this.errandOrderEvaluateInfoDao;
    }

    @Override
    public Message<String> writeEvaluate(ErrandOrderEvaluateInfo errandOrderEvaluateInfo) {
        Long errandOrderId = errandOrderEvaluateInfo.getErrandOrderId();
        ErrandOrderInfo orderInfo = new ErrandOrderInfo();
        orderInfo.setId(errandOrderId);
        orderInfo.setEvaluateState(2);
        errandOrderService.updateByKey(orderInfo);

        errandOrderEvaluateInfoDao.insertEntry(errandOrderEvaluateInfo);
        sendMessage(errandOrderEvaluateInfo);
        return Message.createBySuccess();
    }

    @Override
    @Cacheable(value = GlobalConstant.IRH_TEN_MINUTES_CACHE_KEY, key = "'errandOrderEvaluateDetailById::' + #id")
    public Message<ErrandOrderEvaluateInfo> getDetailById(Long id) {
        ErrandOrderEvaluateInfo evaluateInfo = new ErrandOrderEvaluateInfo();
        evaluateInfo.setState(2);
        evaluateInfo.setId(id);
        List<ErrandOrderEvaluateInfo> infos = errandOrderEvaluateInfoDao.selectEntryList(evaluateInfo);
        if(CollectionUtils.isEmpty(infos)) return Message.createByError("未找到相关信息");
        return Message.createBySuccess(infos.get(0));
    }

    private void sendMessage(ErrandOrderEvaluateInfo evaluateInfo){
        Long holderId = evaluateInfo.getHolderId();
        Long userId = evaluateInfo.getUserId();
        SendUserCenterDto userCenterDto = new SendUserCenterDto();
        userCenterDto.setToId(holderId);
        userCenterDto.setDate(DateUtil.now());
        userCenterDto.setFromId(userId);
        userCenterDto.setNewsType(100);
        userCenterDto.setContent(new StringBuffer().append("你接到的编号为: ").append(evaluateInfo.getErrandOrderCode()).append("的跑腿订单发布者已经进行了评价,评价结果为：").append(evaluateInfo.getEvaluate() == 1 ? "不满意" : "满意").append(",点击查看详情。如果对本次评价存在异议,可以在订单页面详情页面进行申诉").toString());
        userCenterDto.setTargetId(evaluateInfo.getErrandOrderId());
        generateSendMessageService.sendToMq(userCenterDto);
    }
}