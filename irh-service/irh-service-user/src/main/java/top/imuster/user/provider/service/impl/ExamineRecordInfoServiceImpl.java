package top.imuster.user.provider.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.imuster.common.base.dao.BaseDao;
import top.imuster.common.base.domain.BaseDomain;
import top.imuster.common.base.service.BaseServiceImpl;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.dto.rabbitMq.SendUserCenterDto;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.common.core.utils.GenerateSendMessageService;
import top.imuster.goods.api.dto.ESProductDto;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.api.service.GoodsDemandServiceFeignApi;
import top.imuster.goods.api.service.GoodsServiceFeignApi;
import top.imuster.life.api.dto.EsArticleDto;
import top.imuster.life.api.pojo.ArticleInfo;
import top.imuster.life.api.service.ForumServiceFeignApi;
import top.imuster.user.api.pojo.ExamineRecordInfo;
import top.imuster.user.provider.component.Release2ES;
import top.imuster.user.provider.dao.ExamineRecordInfoDao;
import top.imuster.user.provider.service.ExamineRecordInfoService;

import javax.annotation.Resource;

/**
 * ExamineRecordInfoService 实现类
 * @author 黄明人
 * @since 2020-05-21 19:27:46
 */
@Service("examineRecordInfoService")
public class ExamineRecordInfoServiceImpl extends BaseServiceImpl<ExamineRecordInfo, Long> implements ExamineRecordInfoService {

    @Resource
    private ExamineRecordInfoDao examineRecordInfoDao;

    @Autowired
    GenerateSendMessageService generateSendMessageService;

    @Autowired
    GoodsServiceFeignApi goodsServiceFeignApi;

    @Autowired
    GoodsDemandServiceFeignApi goodsDemandServiceFeignApi;

    @Autowired
    ForumServiceFeignApi forumServiceFeignApi;

    @Autowired
    Release2ES release2ES;

    @Override
    public BaseDao<ExamineRecordInfo, Long> getDao() {
        return this.examineRecordInfoDao;
    }

    @Override
    public Message<String> approve(ExamineRecordInfo examineRecordInfo) {
        updateByKey(examineRecordInfo);
        Long targetId = examineRecordInfo.getTargetId();
        String remark = examineRecordInfo.getApproveRemark();
        Long releaseUserId = examineRecordInfo.getReleaseUserId();
        SendUserCenterDto sendUserCenterDto = new SendUserCenterDto();
        sendUserCenterDto.setToId(releaseUserId);
        sendUserCenterDto.setTargetId(targetId);
        sendUserCenterDto.setFromId(-1L);
        sendUserCenterDto.setNewsType(80);
        sendUserCenterDto.setDate(DateUtil.now());
        if(examineRecordInfo.getState() == 1){
            sendUserCenterDto.setContent("您发布的宝贝已经通过人工审核,已取消相关限制,点击可查看");
        }else{
            sendUserCenterDto.setContent("您发布的宝贝没有能通过人工审核, 原因:" + remark);
        }
        generateSendMessageService.sendToMq(sendUserCenterDto);

        getInfoByTargetId(targetId, examineRecordInfo.getType());

        return Message.createBySuccess();
    }



    private void getInfoByTargetId(Long targetId, Integer type){
        if(type == 1){
            ProductInfo productInfo = goodsServiceFeignApi.getProductBriefInfoById(targetId);
            release2ES.save2ES(new ESProductDto(productInfo));
        }else if(type == 2){
            Message<ProductDemandInfo> demandById = goodsDemandServiceFeignApi.getDemandById(targetId);
            if(demandById.getCode() == 200 && demandById.getData() instanceof BaseDomain) release2ES.save2ES(new ESProductDto(demandById.getData()));
        }else if (type == 3){
            ArticleInfo articleInfoById = forumServiceFeignApi.getArticleInfoById(targetId);
            release2ES.save2ES(new EsArticleDto(articleInfoById));
        }
    }
}