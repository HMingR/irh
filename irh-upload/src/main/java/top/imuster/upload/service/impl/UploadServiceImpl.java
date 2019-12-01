package top.imuster.upload.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.upload.service.UploadService;

/**
 * @ClassName: uploadServiceImpl
 * @Description: TODO
 * @author: lpf
 * @date: 2019/11/30 21:18
 */
@Service
public class UploadServiceImpl implements UploadService {

    /**
     * @Description:
     * @Author: lpf
     * @Date: 2019/11/30 21:22
     * @param file
     * @reture: java.lang.String
     **/
    public String uploadImage(MultipartFile file) {
        //文件上传的业务逻辑

        return null;
    }
}
