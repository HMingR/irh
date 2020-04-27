package top.imuster.order.provider.components;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import top.imuster.common.core.utils.DateUtil;
import top.imuster.order.provider.service.ProductDonationApplyInfoService;

import javax.annotation.Resource;

/**
 * @ClassName: DonationApplyAttributeTask
 * @Description: DonationApplyAttributeTask   处理donationApply的点赞收集
 * @author: hmr
 * @date: 2020/4/27 9:46
 */
@Component
public class DonationApplyAttributeTask extends QuartzJobBean {

    private static final Logger log = LoggerFactory.getLogger(DonationApplyAttributeTask.class);

    @Resource
    ProductDonationApplyInfoService productDonationApplyInfoService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("------>{}开始处理donationApply中的down和up记录", DateUtil.now());
        productDonationApplyInfoService.collectDonationAttribute();
        log.info("------>{}完成处理donationApply中的down和up记录", DateUtil.now());
    }
}
