package top.imuster.user.provider.web.controller;

import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.user.api.pojo.ExamineRecordInfo;
import top.imuster.user.provider.service.ExamineRecordInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: AdminExamineRecordController
 * @Description: 审核文章  商品  需求
 * @author: hmr
 * @date: 2020/5/22 11:16
 */
@RestController
@RequestMapping("/admin/examine")
public class AdminExamineRecordController extends BaseController {

    @Resource
    ExamineRecordInfoService examineRecordInfoService;

    @PostMapping
    public Message<Page<ExamineRecordInfo>> getList(@RequestBody Page<ExamineRecordInfo> page){
        page = examineRecordInfoService.selectPage(page.getSearchCondition(), page);
        return Message.createBySuccess(page);
    }

    @GetMapping("/{id}")
    public Message<ExamineRecordInfo> getDetailById(@PathVariable("id") Long id){
        List<ExamineRecordInfo> examineRecordInfos = examineRecordInfoService.selectEntryList(id);
        if(examineRecordInfos != null && !examineRecordInfos.isEmpty()){
            return Message.createBySuccess(examineRecordInfos.get(0));
        }
        return Message.createBySuccess("未找到相关记录");
    }

    @PutMapping
    public Message<String> approve(@RequestBody ExamineRecordInfo examineRecordInfo){
        examineRecordInfo.setApproveId(getCurrentUserIdFromCookie());
        return examineRecordInfoService.approve(examineRecordInfo);
    }
}
