package top.imuster.file.provider.controller;


import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.common.base.controller.BaseController;
import top.imuster.common.base.wrapper.Message;
import top.imuster.file.provider.utils.FastDFSClient;
import top.imuster.file.provider.utils.FastDFSFile;
/**
 * @Description: 文件上传代码实现
 * @Author: lpf
 * @Date: 2019/12/18 11:18
 * @reture: 文件的URL路径
 **/
@RestController
@RequestMapping("/file")
public class FileController extends BaseController {

    @PostMapping("/upload")
    public Message<String> uploadFile(MultipartFile file){
        try{
            //判断文件是否存在
            if (file == null){
                throw new RuntimeException("文件不存在");
            }
            //获取文件的完整名称
            String originalFilename = file.getOriginalFilename();
            if (StringUtils.isEmpty(originalFilename)){
                throw new RuntimeException("文件不存在");
            }

            //获取文件的扩展名称  abc.jpg   jpg
            String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

            //获取文件内容
            byte[] content = file.getBytes();

            //创建文件上传的封装实体类
            FastDFSFile fastDFSFile = new FastDFSFile(originalFilename,content,extName);

            //基于工具类进行文件上传,并接受返回参数  String[]
            String[] uploadResult = FastDFSClient.upload(fastDFSFile);

            //封装返回结果
            String url = FastDFSClient.getTrackerUrl()+uploadResult[0]+"/"+uploadResult[1];
            return Message.createBySuccess("文件上传成功",url);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Message.createByError("文件上传失败");
    }
}
