package top.imuster.user.provider.web.controller;

import cn.hutool.core.io.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.common.base.wrapper.Message;
import top.imuster.user.api.pojo.IrhTemplate;
import top.imuster.user.provider.exception.UserException;
import top.imuster.user.provider.service.IrhTemplateService;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @ClassName: IrhTemplateController
 * @Description: irh平台中forum模块和goods模块的静态模板
 * @author: hmr
 * @date: 2020/2/18 19:45
 */
@Api
@RestController
@RequestMapping("/template")
public class IrhTemplateController {

    @Resource
    IrhTemplateService irhTemplateService;

    @ApiOperation("查看所有的有效模板")
    @GetMapping
    public Message<List<IrhTemplate>> list(){
        IrhTemplate condition = new IrhTemplate();
        condition.setState(2);
        List<IrhTemplate> irhTemplates = irhTemplateService.selectEntryList(condition);
        return Message.createBySuccess(irhTemplates);
    }

    @ApiOperation("添加模板，使用表单形式")
    @PutMapping
    public Message<String> add(IrhTemplate irhTemplate, @RequestParam("file") MultipartFile file){
        String stringFromFile = getStringFromFile(file);
        irhTemplate.setContext(stringFromFile);
        irhTemplateService.insert(irhTemplate);
        return Message.createBySuccess();
    }

    @ApiOperation("修改模板，使用表单形式")
    @PostMapping
    public Message<String> update(IrhTemplate irhTemplate, @RequestParam("file")MultipartFile file){
        String stringFromFile = getStringFromFile(file);
        irhTemplate.setContext(stringFromFile);
        irhTemplateService.updateByKey(irhTemplate);
        return Message.createBySuccess();
    }

    @ApiOperation("根据模板id查看模板详情")
    @GetMapping("/{id}")
    public Message<IrhTemplate> getDetail(@PathVariable("id") Long id){
        IrhTemplate irhTemplate = irhTemplateService.selectEntryList(id).get(0);
        return Message.createBySuccess(irhTemplate);
    }

    private String getStringFromFile(MultipartFile file){
        File toFile;
        InputStream ins = null;
        try{
            if(!file.isEmpty()){
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                FileUtils.copyInputStreamToFile(ins, toFile);
                String s = FileUtils.readFileToString(toFile, "UTF-8");
                return s;
            }
            return null;
        }catch (Exception e){
            throw new UserException("处理文件时出错,请联系管理员或不使用文件上传模板");
        }finally {
            try {
                ins.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
