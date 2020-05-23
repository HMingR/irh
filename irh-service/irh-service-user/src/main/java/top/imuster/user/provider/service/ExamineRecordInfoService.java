package top.imuster.user.provider.service;


import top.imuster.common.base.service.BaseService;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.ExamineRecordInfo;

/**
 * ExamineRecordInfoService接口
 * @author 黄明人
 * @since 2020-05-21 19:27:46
 */
public interface ExamineRecordInfoService extends BaseService<ExamineRecordInfo, Long> {

    /**
     * @Author hmr
     * @Description 审核
     * @Date: 2020/5/22 11:23
     * @param examineRecordInfo
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    Message<String> approve(ExamineRecordInfo examineRecordInfo);
}