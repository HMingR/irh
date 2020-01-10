package top.imuster.goods.web.controller;

import io.swagger.annotations.ApiOperation;
import org.codehaus.jackson.map.Serializers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.domain.Page;
import top.imuster.common.base.wrapper.Message;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.goods.api.pojo.ProductInfo;
import top.imuster.goods.exception.GoodsException;
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
@ConfigurationProperties(prefix = "image")
public class DemandProductController extends BaseController {

    //todo
    private static List<String> fileTypes;

    @Resource
    ProductInfoService productInfoService;

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    @ApiOperation("条件查询")
    @PostMapping("/list")
    public Message list(Page<ProductInfo> page){
        return null;
    }

    @ApiOperation("发布需求,采用表单的形式，不采用json形式传递其他信息，且上传的图片的<input>或其他标签name必须是file")
    @PutMapping
    public Message add(@RequestParam("file") MultipartFile file, ProductInfo productInfo){
        try{
            if(!file.isEmpty()){
                int last = file.getOriginalFilename().length();
                String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."), last);
                if(null == fileType || "jpg".equals(fileType) || "png".equals(fileType)){

                }
                fileServiceFeignApi.upload(file);
            }
            System.out.println(productInfo);
            // todo 需要向消息队列中发送生成详情页和调用fil模块进行上传图片(并且返回图片的url)
            return Message.createBySuccess("asdf");

        }catch (Exception e){
            logger.error("发布需求失败",e.getMessage(),e);
            throw new GoodsException("需求发布失败");
        }
    }

    @PostMapping
    public Message edit(ProductInfo productInfo){
        return null;
    }

    @ApiOperation("删除用户自己发布的需求")
    @DeleteMapping("/{productId}")
    public Message del(@PathVariable("productId") Long id){

        return null;
    }
}
