package top.imuster.file.api.service.hystrix;

import feign.hystrix.FallbackFactory;
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
public class FileServiceFeignApiHystrix implements FallbackFactory<FileServiceFeignApi> {

    @Override
    public FileServiceFeignApi create(Throwable throwable) {
        log.error("FileServiceFeignApiHystrix---->远程调用该模块出现异常,错误信息为{}", throwable.getMessage());
        return new FileServiceFeignApi() {
            @Override
            public Message<String> upload(MultipartFile file){
                //todo 上传失败之后需要给一个默认的url
                log.error("FileServiceFeignApiHystrix---->上传文件失败");
                return null;
            }

            @Override
            public Message<String> deleteByName(String name) {
                log.error("FileServiceFeignApiHystrix---->根据uri删除文件失败,文件名为{}",name);
                return Message.createByError();
            }
        };
    }
}
