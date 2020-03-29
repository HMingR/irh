package top.imuster.file.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.common.base.wrapper.Message;
import top.imuster.file.api.service.hystrix.FileServiceFeignApiHystrix;

/**
 * @ClassName: FileServiceFeignApi
 * @Description: 文件模块对外提供的接口
 * @author: hmr
 * @date: 2020/1/10 20:16
 */
@FeignClient(value = "file-service", path = "/file", fallbackFactory = FileServiceFeignApiHystrix.class)
public interface FileServiceFeignApi {

    /**
     * @Description: 文件上传
     * @Author: hmr
     * @Date: 2020/1/10 20:17
     * @param file
     * @reture: top.imuster.common.base.wrapper.Message 上传成功
     *
     * @return*/
    @PostMapping(produces = { MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Message<String> upload(@RequestPart("file") MultipartFile file);

    /**
     * @Author hmr
     * @Description 根据文件名称删除指定文件
     * @Date: 2020/2/20 10:18
     * @param name
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @DeleteMapping
    Message<String> deleteByName(@RequestBody String fileUri);
}
