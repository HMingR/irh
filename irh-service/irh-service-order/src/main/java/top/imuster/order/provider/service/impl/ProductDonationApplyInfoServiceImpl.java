package top.imuster.order.provider.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.SendUserCenterDto;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.utils.DateUtils;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.order.api.pojo.OrderInfo;
import top.imuster.order.api.pojo.ProductDonationApplyInfo;
import top.imuster.order.provider.dao.ProductDonationApplyInfoDao;
import top.imuster.order.provider.exception.OrderException;
import top.imuster.order.provider.service.OrderInfoService;
import top.imuster.order.provider.service.ProductDonationApplyInfoService;
import top.imuster.order.provider.service.ProductDonationOrderRelService;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * ProductDonationApplyInfoService 实现类
 * @author 黄明人
 * @since 2020-04-14 16:45:13
 */
@Service("productDonationApplyInfoService")
public class ProductDonationApplyInfoServiceImpl extends BaseServiceImpl<ProductDonationApplyInfo, Long> implements ProductDonationApplyInfoService {

    @Resource
    private ProductDonationApplyInfoDao productDonationApplyInfoDao;

    @Resource
    private ProductDonationOrderRelService productDonationOrderRelService;

    @Resource
    GenerateSendMessageService generateSendMessageService;

    @Resource
    private OrderInfoService orderInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public BaseDao<ProductDonationApplyInfo, Long> getDao() {
        return this.productDonationApplyInfoDao;
    }

    @Override
    public Message<String> apply(UserDto userInfo, ProductDonationApplyInfo applyInfo) {
        if(userInfo.getUserType().getType() != 40){
            return Message.createByError("权限不足");
        }
        applyInfo.setApplyUserId(userInfo.getUserId());
        productDonationApplyInfoDao.insertEntry(applyInfo);
        return Message.createBySuccess();
    }

    @Override
    public Message<String> approve(ProductDonationApplyInfo approveInfo) {
        productDonationApplyInfoDao.updateByKey(approveInfo);
        return Message.createBySuccess();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message<String> grant(Long operatorId, Long applyId) throws JsonProcessingException {
        ProductDonationApplyInfo applyInfo = productDonationApplyInfoDao.selectAvailableApplyById(applyId);
        if(applyInfo == null) return Message.createByError("当前要申请的工单还不能发放金额");
        List<OrderInfo> orderInfos = orderInfoService.getAllDonationOrderInfo();
        if(orderInfos == null || orderInfos.isEmpty()) return Message.createByError("资金不足");
        //申请金额
        Double applyAmount = Double.parseDouble(applyInfo.getApplyAmount());

        //按照订单的金额升序排列
        List<OrderInfo> list = orderInfos.stream().sorted(Comparator.comparingDouble(OrderInfo::getMoney)).collect(Collectors.toList());
        BigDecimal totalMoney = new BigDecimal(0.00);
        ArrayList<OrderInfo> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++){
            String paymentMoney = orderInfos.get(i).getPaymentMoney();
            double money = Double.parseDouble(paymentMoney);
            if(totalMoney.add(new BigDecimal(money)).doubleValue() > applyAmount){
                break;
            }
            totalMoney.add(new BigDecimal(money));
            res.add(list.get(i));
        }

        //将操作者信息保存到DB中
        applyInfo.setGrantUser(operatorId);
        productDonationApplyInfoDao.updateByKey(applyInfo);

        String resString = objectMapper.writeValueAsString(res);

        //将选择出来的orderInfo保存到redis里面，并且设置2分钟的过期时间
        String redisKey = RedisUtil.getDonationApplyCode(String.valueOf(applyId));
        redisTemplate.opsForValue().set(redisKey, resString, 2, TimeUnit.MINUTES);
        return Message.createBySuccess(String.valueOf(totalMoney.doubleValue()));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Message<String> determine(Long applyId, Long operatorId) throws IOException {
        List<ProductDonationApplyInfo> list = productDonationApplyInfoDao.selectEntryList(applyId);
        if(list == null || list.isEmpty()) return Message.createByError("未找到相关申请信息,请刷新后重试");
        ProductDonationApplyInfo applyInfo = list.get(0);
        String jString = (String) redisTemplate.opsForValue().get(RedisUtil.getDonationApplyCode(String.valueOf(applyId)));
        List<OrderInfo> res = objectMapper.readValue(jString, new TypeReference<List<OrderInfo>>() {});
        res.stream().forEach(orderInfo -> {
            Integer oldOrderVersion = orderInfoService.getOrderVersionById(orderInfo.getId());
            if (!oldOrderVersion.equals(orderInfo.getOrderVersion())) throw new OrderException("选择的订单已经被别人使用,请刷新后重新生成");
            orderInfo.setOrderVersion(orderInfo.getOrderVersion() + 1);
            orderInfo.setState(60);
            orderInfoService.updateByKey(orderInfo);

            //发送消息
            SendUserCenterDto send = new SendUserCenterDto();
            send.setToId(orderInfo.getSalerId());
            send.setFromId(-1L);
            send.setDate(DateUtils.now());
            send.setContent(new StringBuffer().append("您的爱心订单已经被用于").append(applyInfo.getReason()).toString());
            generateSendMessageService.sendToMq(send);
        });
        redisTemplate.delete(RedisUtil.getDonationApplyCode(String.valueOf(applyId)));
        return Message.createBySuccess();
    }

    @Override
    public Message<Page<ProductDonationApplyInfo>> finishApplyList(Integer pageSize, Integer currentPage) {
        HashMap<String, Integer> param = new HashMap<>();
        param.put("pageSize", pageSize);
        param.put("startIndex", ((currentPage < 1 ? 1 : currentPage) - 1));
        List<ProductDonationApplyInfo> list = productDonationApplyInfoDao.selectFinishApplyList(param);
        Integer count = productDonationApplyInfoDao.selectApplyCountByState(5);
        Page<ProductDonationApplyInfo> page = new Page<>();
        page.setData(list);
        page.setTotalCount(count);
        return Message.createBySuccess(page);
    }

    @Override
    public Message<Page<ProductDonationApplyInfo>> unfinishApplyList(Integer pageSize, Integer currentPage) {
        HashMap<String, Integer> param = new HashMap<>();
        param.put("pageSize", pageSize);
        param.put("startIndex", ((currentPage < 1 ? 1 : currentPage) - 1));
        List<ProductDonationApplyInfo> list = productDonationApplyInfoDao.selectUnfinishApplyList(param);
        Integer count = productDonationApplyInfoDao.selectApplyCountByState(2);
        Page<ProductDonationApplyInfo> page = new Page<>();
        page.setTotalCount(count);
        page.setData(list);
        return Message.createBySuccess(page);
    }

    @Override
    public Message<List<ProductDonationApplyInfo>> getNewestApply() {
        List<ProductDonationApplyInfo> applyInfos = productDonationApplyInfoDao.selectNewestApplyInfo();
        return Message.createBySuccess(applyInfos);
    }

    @Override
    public Message<ProductDonationApplyInfo> getApplyInfoById(Integer type, Long applyId) {
        ProductDonationApplyInfo applyInfo;
        if(type == 1){
            applyInfo = productDonationApplyInfoDao.selectApplyInfoById(applyId);
            List<OrderInfo> useOrders = productDonationOrderRelService.getOrderInfoByApplyId(applyId);
            applyInfo.setUserOrders(useOrders);
        }else{
            applyInfo = productDonationApplyInfoDao.selectApplyInfoById(applyId);
        }
        return Message.createBySuccess(applyInfo);
    }

}