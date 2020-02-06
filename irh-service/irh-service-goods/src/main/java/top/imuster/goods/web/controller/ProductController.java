package top.imuster.goods.web.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.common.core.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.annotation.BrowserTimesAnnotation;
import top.imuster.common.core.dto.SendMessageDto;
import top.imuster.common.core.enums.BrowserType;
import top.imuster.common.core.validate.ValidateGroup;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.exception.GoodsException;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName: ProductController
 * @Description: 处理商品的controller
 * @author: hmr
 * @date: 2019/12/1 14:53
 */
@Api("二手商品controller")
@Controller
@RequestMapping("/goods/es")
public class ProductController extends BaseController {

    @Value("${image.fileTypes}")
    private List<String> types;

    @Resource
    ProductInfoService productInfoService;

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    @ApiOperation("会员发布二手商品,采用表单的形式，不采用json形式，且上传的图片的<input>或其他标签name必须是file")
    @PutMapping
    public Message insertProduct(@RequestParam("file") MultipartFile file, @Validated(ValidateGroup.releaseGroup.class) ProductInfo productInfo, BindingResult bindingResult) throws Exception {
        validData(bindingResult);
        Long userId = getCurrentUserIdFromCookie();
        productInfo.setConsumerId(userId);
        if(!file.isEmpty()){
            int last = file.getOriginalFilename().length();
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), last);
            if(!types.contains(fileType)){
                return Message.createByError("图片格式不正确,请更换图片格式");
            }
            String url = fileServiceFeignApi.upload(file).getText();
            productInfo.setMainPicUrl(url);
        }
        productInfoService.insertEntry(productInfo);
        SendMessageDto sendMessageDto = new SendMessageDto();
        sendMessageDto.setBody(new ObjectMapper().writeValueAsString(productInfo));
        productInfoService.generateDetailPage(sendMessageDto);
        return Message.createBySuccess("发布商品成功");
    }


    @ApiOperation(value = "条件分页查询商品列表", httpMethod = "POST")
    @PostMapping("/list")
    public Message productList(Page<ProductInfo> page, ProductInfo productInfo) throws GoodsException{
        Page<ProductInfo> productInfoPage = productInfoService.selectPage(productInfo, page);
        return Message.createBySuccess(productInfoPage);
    }

    @ApiOperation(value = "根据id获得商品信息", httpMethod = "GET")
    @BrowserTimesAnnotation(browserType = BrowserType.ES_DEMAND_PRODUCT)
    @GetMapping("/{id}")
    public Message getProductById(@PathVariable("id")Long id){
        ProductInfo productInfo = productInfoService.selectEntryList(id).get(0);
        return Message.createBySuccess(productInfo);
    }

    @ApiOperation(value = "修改商品信息", httpMethod = "POST")
    @PostMapping("/edit")
    public Message editProduct(@RequestBody @Validated(ValidateGroup.editGroup.class) ProductInfo productInfo, BindingResult bindingResult) throws GoodsException {
        validData(bindingResult);
        int i = productInfoService.updateByKey(productInfo);
        if(i != 0){
            return Message.createBySuccess();
        }
        return Message.createByError();
    }

    /**
     * @Description: 用户下架商品
     * @Author: hmr
     * @Date: 2019/12/27 15:11
     * @param id
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @ApiOperation("用户下架商品")
    @DeleteMapping("/{id}")
    public Message delProduct(@PathVariable("id") Long id){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setId(id);
        productInfo.setState(1);
        int i = productInfoService.updateByKey(productInfo);
        if(i != 0){
            return Message.createBySuccess("操作成功");
        }
        return Message.createByError("更新失败,找不到对应的商品,请刷新后重试");
    }

    /**
     * @Author hmr
     * @Description 获得商品详情页
     * @Date: 2020/1/21 9:45
     * @param
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     **/
    @GetMapping("/detail")
    public Message<String> getProductDetailUrl(){

        return null;
    }

}
