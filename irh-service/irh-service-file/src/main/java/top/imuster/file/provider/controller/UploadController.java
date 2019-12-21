package top.imuster.file.provider.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.file.provider.service.UploadService;

/**
 * @ClassName: UploadController
 * @Description: TODO
 * @author: lpf
 * @date: 2019/11/30 21:12
 */
@Controller
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    /**
     * @Description: 测试图片上传
     * @Author: lpf
     * @Date: 2019/11/30 21:14
     * @param
     * @reture: org.springframework.http.ResponseEntity<java.lang.String>
     **/
    @PostMapping("image")
    public ResponseEntity<String> uploadImage(@RequestParam("file")MultipartFile file) {
        String url = uploadService.uploadImage(file);
        if (StringUtils.isBlank(url)) {
            return ResponseEntity.badRequest().build();   //响应400
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(url);   //响应数据url
    }

}
