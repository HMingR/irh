package top.imuster.goods.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.goods.api.pojo.ProductDemandInfo;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.service.ProductDemandInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName: DemandProductController
 * @Description: 会员自己发布自己的需求
 * @author: hmr
 * @date: 2019/12/31 20:35
 */
@Api("会员发布的需求")
@RestController
@RequestMapping("/goods/demand")
@PropertySource("classpath:application.yml")
public class DemandProductController extends BaseController {
    @Resource
    ProductDemandInfoService productDemandInfoService;

    @ApiOperation(value = "条件查询", httpMethod = "POST")
    @PostMapping("/list")
    public Message list(Page<ProductInfo> page){
        return null;
    }

    @ApiOperation(value = "发布需求", httpMethod = "PUT")
    @PutMapping
    public Message add(HttpServletRequest request, @RequestBody @Validated(ValidateGroup.releaseGroup.class) ProductDemandInfo productDemandInfo, BindingResult bindingResult) throws Exception {
        validData(bindingResult);
        Long userId = getIdByToken(request);
        productDemandInfo.setConsumerId(userId);
        productDemandInfo.setState(2);
        int i = productDemandInfoService.insertEntry(productDemandInfo);
        if(i == 1){
            return Message.createBySuccess("发布成功");
        }
        return Message.createByError("发布失败");
    }

    @ApiOperation(value = "根据id查询", httpMethod = "GET")
    @GetMapping("/{id}")
    public Message getById(@PathVariable("id") Long id){
        ProductDemandInfo productDemandInfo = productDemandInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(productDemandInfo);
    }

    @ApiOperation(value = "根据主键id修改信息", httpMethod = "POST")
    @PostMapping
    public Message edit(@RequestBody @Validated(ValidateGroup.editGroup.class) ProductDemandInfo productDemandInfo, BindingResult bindingResult){
        validData(bindingResult);
        productDemandInfoService.updateByKey(productDemandInfo);
        return Message.createBySuccess("修改成功");
    }

    @ApiOperation(value = "删除用户自己发布的需求", httpMethod = "DELETE")
    @DeleteMapping("/{id}")
    public Message del(@PathVariable("id") Long id){
        ProductDemandInfo condition = new ProductDemandInfo();
        condition.setId(id);
        condition.setState(1);
        int i = productDemandInfoService.updateByKey(condition);
        if(i == 1){
            return Message.createBySuccess("删除成功");
        }
        return Message.createByError("删除失败");
    }
}
