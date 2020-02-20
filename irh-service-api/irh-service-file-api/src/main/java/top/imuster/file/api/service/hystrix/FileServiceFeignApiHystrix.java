package top.imuster.file.api.service.hystrix;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.common.base.wrapper.Message;
import top.imuster.file.api.service.FileServiceFeignApi;

/**
 * @ClassName: FileServiceFeignApiHystrix
 * @Description: file模块的feign代理的服务降级处理
 * @author: hmr
 * @date: 2020/1/10 20:33
 */
@Slf4j
@Component
public class FileServiceFeignApiHystrix implements FileServiceFeignApi {

    @Override
    public Message upload(MultipartFile file){
        log.error("FileServiceFeignApiHystrix---->远程调用fileService报错");
        return null;
    }

    @Override
    public Message deleteByName(String name) {
        log.error("根据uri删除文件失败,文件名为{}",name);
        return null;
    }

    @Override
    public String test() {
        log.error("测试失败");
        return "测试失败";
    }
}
