package top.imuster.upload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: UploadService
 * @Description: TODO
 * @author: lpf
 * @date: 2019/11/30 21:15
 */
public interface UploadService {

    String uploadImage(MultipartFile file);
}
