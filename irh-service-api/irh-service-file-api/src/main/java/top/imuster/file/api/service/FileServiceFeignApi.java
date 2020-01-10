package top.imuster.file.api.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    Message upload(@RequestParam("file") MultipartFile file) throws Exception;

}
