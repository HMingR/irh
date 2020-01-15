package top.imuster.goods.web.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.util.logging.resources.logging;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.service.ProductInfoService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: DemandProductController
 * @Description: 会员自己发布自己的需求
 * @author: hmr
 * @date: 2019/12/31 20:35
 */
@RestController
@RequestMapping("/goods/demand")
@PropertySource("classpath:application.yml")
public class DemandProductController extends BaseController {
    @Value("${image.fileTypes}")
    private List<String> types;

    @Value(("${logging.config}"))
    private String x;

    @Resource
    ProductInfoService productInfoService;

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    @ApiOperation(value = "条件查询", httpMethod = "POST")
    @PostMapping("/list")
    public Message list(Page<ProductInfo> page){
        return null;
    }

    @ApiOperation(value = "发布需求,采用表单的形式，不采用json形式传递其他信息，且上传的图片的<input>或其他标签name必须是file", httpMethod = "PUT")
    @PutMapping
    public Message add(@RequestParam("file") MultipartFile file, ProductInfo productInfo, BindingResult bindingResult) throws Exception {
        validData(bindingResult);
        if(!file.isEmpty()){
            int last = file.getOriginalFilename().length();
            String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), last);
            if(null == fileType || "jpg".equals(fileType) || "png".equals(fileType)){
                return Message.createByError("图片格式不正确,请更换图片");
            }
            String url = fileServiceFeignApi.upload(file).getText();
            productInfo.setMainPicUrl(url);
        }
        productInfoService.insertEntry(productInfo);
        // todo 需要向消息队列中发送生成详情页的消息
        return Message.createBySuccess("发布商品成功");
    }

    @ApiOperation(value = "删除用户自己发布的需求", httpMethod = "DELETE")
    @DeleteMapping("/{productId}")
    public Message del(@PathVariable("productId") Long id){

        return null;
    }
}
