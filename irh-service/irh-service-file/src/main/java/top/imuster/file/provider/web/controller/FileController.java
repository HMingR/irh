package top.imuster.file.provider.web.controller;


import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.common.base.wrapper.Message;
import top.imuster.common.core.controller.BaseController;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.file.provider.exception.FileException;
import top.imuster.file.provider.file.FastDFSFile;
import top.imuster.file.provider.utils.FastDFSUtil;

/**
 * @Description: 文件上传代码实现
 * @Author: lpf
 * @Date: 2019/12/18 11:18
 * @reture: 文件的URL路径
 **/
@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController extends BaseController implements FileServiceFeignApi {

    /**
     * @Description: 返回的是相对路径
     * @Author: lpf
     * @Date: 2019/12/23 12:54
     * @param file
     * @reture: top.imuster.common.base.wrapper.Message<java.lang.String>
     *
     * @return*/
    @Override
    @PostMapping
    public Message<String> upload(@RequestParam(value = "file")MultipartFile file) {
        try{
            //封装文件信息
            FastDFSFile fastDFSFile = new FastDFSFile(
                    file.getOriginalFilename(),   //文件名 1.jpg
                    file.getBytes(),              //文件的字节数组
                    org.springframework.util.StringUtils.getFilenameExtension(file.getOriginalFilename())    //获取文件拓展名
            );
            //调用FastDFSUtil工具类将文件上传到FastDFS中
            String[] uploads = FastDFSUtil.upload(fastDFSFile);

            //拼接访问地址 url = http://39.105.0.169:8080/group1/M00/00/00/hjdfhjhfjs3278yf47.jpg
            //String url = "http://39.105.0.169:8080/" + uploads[0] + "/" + uploads[1];
            return Message.createBySuccess(uploads[0] + "/" + uploads[1]);
        }catch (Exception e){
            logger.error("上传文件失败{}",e.getMessage(),e);
            throw new FileException("文件上传失败");
        }

    }

    /**
     * @Author hmr
     * @Description 删除文件
     * @Date: 2020/2/7 16:39
     * @param fileUri
     * @reture: top.imuster.common.base.wrapper.Message
     **/
    @Override
    @DeleteMapping
    public Message<String> deleteByName(@RequestParam("fileUri") String fileUri){
        int index = fileUri.indexOf("/");
        String groupName = fileUri.substring(0, index);
        String fileName = fileUri.substring(index + 1, fileUri.length());
        try {
            FastDFSUtil.deleteFile(groupName, fileName);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new FileException(e.getMessage());
        }
        return Message.createBySuccess();
    }



}
