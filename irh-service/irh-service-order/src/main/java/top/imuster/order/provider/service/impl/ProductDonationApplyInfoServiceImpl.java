package top.imuster.order.provider.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.UserDto;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.common.core.utils.RedisUtil;
import top.imuster.order.api.dto.DonationAttributeDto;
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
import java.util.*;
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
        Long id = approveInfo.getId();
        productDonationApplyInfoDao.updateByKey(approveInfo);

        Integer state = approveInfo.getState();
        if(state == 3){
            ProductDonationApplyInfo applyInfo = productDonationApplyInfoDao.selectEntryList(id).get(0);
            SendUserCenterDto sendUserCenterDto = new SendUserCenterDto();
            sendUserCenterDto.setNewsType(70);
            sendUserCenterDto.setToId(approveInfo.getApplyUserId());
            sendUserCenterDto.setContent(new StringBuffer().append("您好,您于").append(applyInfo.getCreateTime()).append("提交的《").append(applyInfo.getTitle()).append("》公益基金申请未能通过,失败原因是:").append(applyInfo.getRemark()).toString());
            sendUserCenterDto.setFromId(-1L);
            sendUserCenterDto.setDate(DateUtil.now());
            generateSendMessageService.sendToMq(sendUserCenterDto);
        }
        return Message.createBySuccess();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Message<String> grant(Long applyId, Long operatorId) throws JsonProcessingException {
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
        redisTemplate.opsForValue().set(redisKey, resString, 5, TimeUnit.MINUTES);
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
            send.setDate(DateUtil.now());
            send.setContent(new StringBuffer().append("您的爱心订单已经被用于").append(applyInfo.getReason()).toString());
            send.setNewsType(50);
            send.setResourceId(applyId);
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

        /*String downKey = RedisUtil.getDonationApplyAttributeKey(1);
        String upKey = RedisUtil.getDonationApplyAttributeKey(2);
        list.stream().forEach(applyInfo -> {
            Long id = applyInfo.getId();
            Integer down =(Integer) redisTemplate.opsForHash().get(downKey, String.valueOf(id));
            Integer up =(Integer) redisTemplate.opsForHash().get(upKey, String.valueOf(id));
            applyInfo.setUserDownTotal(down);
            applyInfo.setUserUpTotal(up);
        });*/
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
        ProductDonationApplyInfo applyInfo = new ProductDonationApplyInfo();
        if(type == 5){
            applyInfo = productDonationApplyInfoDao.selectApplyInfoById(applyId);
            List<OrderInfo> useOrders = productDonationOrderRelService.getOrderInfoByApplyId(applyId);
            applyInfo.setUserOrders(useOrders);
        }else{
            applyInfo.setId(applyId);
            List<ProductDonationApplyInfo> infos = productDonationApplyInfoDao.selectEntryList(applyInfo);
            if(infos == null || infos.isEmpty()) return Message.createByError("没有找到相关的申请,请刷新后重试");
            applyInfo = infos.get(0);
            String downKey = RedisUtil.getDonationApplyAttributeKey(1);
            String upKey = RedisUtil.getDonationApplyAttributeKey(2);
            Integer down =(Integer) redisTemplate.opsForHash().get(downKey, String.valueOf(applyId));
            Integer up =(Integer) redisTemplate.opsForHash().get(upKey, String.valueOf(applyId));
            down = down == null ? 0 : down;
            up = up == null ? 0 : up;
            applyInfo.setUserDownTotal(applyInfo.getUserDownTotal() + down);
            applyInfo.setUserUpTotal(applyInfo.getUserUpTotal() + up);
        }
        return Message.createBySuccess(applyInfo);
    }

    @Override
    public Message<String> upOrDownApply(Integer type, Long targetId) {
        String redisKey = RedisUtil.getDonationApplyAttributeKey(type);
        redisTemplate.opsForHash().increment(redisKey, String.valueOf(targetId), 1);
        return Message.createBySuccess();
    }

    @Override
    public void collectDonationAttribute() {
        List<DonationAttributeDto> downList = getListByRedisKey(RedisUtil.getDonationApplyAttributeKey(1));
        List<DonationAttributeDto> upList = getListByRedisKey(RedisUtil.getDonationApplyAttributeKey(2));
        if(CollectionUtils.isNotEmpty(upList)){
            Integer upResTotal = productDonationApplyInfoDao.updateUpTotal(upList);
            log.info("更新了{}条赞成记录", upResTotal);
        }
        if(CollectionUtils.isNotEmpty(downList)){
            Integer downResTotal = productDonationApplyInfoDao.updateDownTotal(downList);
            log.info("更新了{}条反对记录", downResTotal);
        }
    }

    @Override
    public Message<Page<ProductDonationApplyInfo>> getApplyList(Page<ProductDonationApplyInfo> page) {
        int pageSize = page.getPageSize();
        int currentPage = page.getCurrentPage();
        ProductDonationApplyInfo searchCondition = page.getSearchCondition();
        if(searchCondition == null){
            searchCondition = new ProductDonationApplyInfo();
            searchCondition.setOrderField("user_up_total");
            searchCondition.setOrderFieldType("DESC");
            searchCondition.setStartIndex((currentPage - 1) * pageSize);
            searchCondition.setEndIndex(pageSize);
            page.setSearchCondition(searchCondition);
        }
        Integer state = searchCondition.getState();
        if(state != null && (state == 5 || state == 6)) return Message.createByError("参数异常");

        Integer count = productDonationApplyInfoDao.selectEntryListCount(searchCondition);
        List<ProductDonationApplyInfo> res = productDonationApplyInfoDao.selectApplyListByCondition(searchCondition);

        String upKey = RedisUtil.getDonationApplyAttributeKey(1);
        String downKey = RedisUtil.getDonationApplyAttributeKey(2);
        res.stream().forEach(applyInfo -> {
            Object up = redisTemplate.opsForHash().get(upKey, String.valueOf(applyInfo.getId()));
            if(up instanceof Integer) applyInfo.setUserUpTotal(applyInfo.getUserUpTotal() + Integer.parseInt(String.valueOf(up)));
            Object down = redisTemplate.opsForHash().get(downKey, String.valueOf(applyInfo.getId()));
            if(down instanceof Integer) applyInfo.setUserDownTotal(applyInfo.getUserDownTotal() + Integer.parseInt(String.valueOf(down)));
        });

        page.setTotalCount(count);
        page.setData(res);
        return Message.createBySuccess(page);
    }

    @Override
    public Message<Page<ProductDonationApplyInfo>> getApproveList(Page<ProductDonationApplyInfo> page) {
        ProductDonationApplyInfo searchCondition = page.getSearchCondition();
        if(searchCondition == null){
            searchCondition = new ProductDonationApplyInfo();
            searchCondition.setOrderField("update_time");
            searchCondition.setOrderFieldType("DESC");
            searchCondition.setState(4);
            page.setSearchCondition(searchCondition);
        }
        Integer state = searchCondition.getState();
        if(state == null && state != 4 && state != 5) searchCondition.setState(4);
        page = this.selectPage(searchCondition, page);
        return Message.createBySuccess(page);
    }

    @Override
    @Transactional
    public Message<String> determine(ProductDonationApplyInfo applyInfo, Long userId) {
        List<OrderInfo> orderInfos = applyInfo.getOrderList();
        if(CollectionUtils.isEmpty(orderInfos)) return Message.createByError("选择的订单不能为空");
        Double applyAmount = 0D;
        Integer index = 0;
        for (OrderInfo orderInfo : orderInfos){
            index++;
            String paymentMoney = orderInfo.getPaymentMoney();
            applyAmount += Double.parseDouble(paymentMoney);
            Integer temp = orderInfoService.updateOrderStateByIdAndVersion(orderInfo.getId(), orderInfo.getOrderVersion(), 100);
            if(temp != 1) throw new OrderException("您选择的第 " + index + " 个订单信息已经被修改,请刷新后重试");
        }
        applyInfo.setState(5);
        applyInfo.setPaymentAmount(applyAmount.toString());
        int i = productDonationApplyInfoDao.updateByKey(applyInfo);
        if(i != 1) throw new OrderException("更新申请信息失败");
        return Message.createBySuccess();
    }

    private List<DonationAttributeDto> getListByRedisKey(String redisKey){
        Cursor<Map.Entry<Object, Object>> down = redisTemplate.opsForHash().scan(redisKey, ScanOptions.NONE);
        DonationAttributeDto attributeDto = new DonationAttributeDto();
        ArrayList<DonationAttributeDto> list = new ArrayList<>();
        while(down.hasNext()){
            Map.Entry<Object, Object> next = down.next();
            if(next.getKey() == null || next.getValue() == null) continue;
            String key = String.valueOf(next.getKey());
            if(StringUtils.isEmpty(key)) continue;
            long targetId = Long.parseLong(key);
            String value = String.valueOf(next.getValue());
            if(StringUtils.isEmpty(value)) continue;
            int total = Integer.parseInt(value);
            attributeDto.setTargetId(targetId);
            attributeDto.setTotal(total);
            list.add(attributeDto);
            redisTemplate.opsForHash().delete(redisKey, key);
        }
        return list;
    }

}