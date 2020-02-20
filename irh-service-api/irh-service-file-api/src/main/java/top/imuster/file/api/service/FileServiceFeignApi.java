package top.imuster.file.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.common.base.wrapper.Message;
import top.imuster.file.api.service.hystrix.FileServiceFeignApiHystrix;

/**
 * @ClassName: FileServiceFeignApi
 * @Description: 文件模块对外提供的接口
 * @author: hmr
 * @date: 2020/1/10 20:16
 */
@FeignClient(value = "file-service", path = "/file", fallback = FileServiceFeignApiHystrix.class)
public interface FileServiceFeignApi {

    /**
     * @Description: 文件上传
     * @Author: hmr
     * @Date: 2020/1/10 20:17
     * @param file
     * @reture: top.imuster.common.base.wrapper.Message 上传成功
     **/
    @PostMapping
    Message upload(@RequestParam("file") MultipartFile file);

    /**
     * @Author hmr
     * @Description 根据文件名称删除指定文件
     * @Date: 2020/2/20 10:18
     * @param name
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @DeleteMapping("/{name}")
    Message deleteByName(@PathVariable("name") String name);

    @GetMapping("/test")
    String test();

}
