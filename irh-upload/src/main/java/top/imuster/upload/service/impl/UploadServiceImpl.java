package top.imuster.upload.service.impl;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.imuster.upload.service.UploadService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName: uploadServiceImpl
 * @Description: TODO
 * @author: lpf
 * @date: 2019/11/30 21:18
 */
@Service
public class UploadServiceImpl implements UploadService {

    private static final List<String> CONTENT_TYPES = Arrays.asList("image/gif","image/jpeg","image/png");

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadServiceImpl.class);

    /**
     * @Description: 文件上传功能实现
     * @Author: lpf
     * @Date: 2019/11/30 21:22
     * @param file
     * @reture: java.lang.String
     **/
    public String uploadImage(MultipartFile file) {
        //文件上传的业务逻辑
        String originalFilename = file.getOriginalFilename();

        //校验文件类型
        String contentType = file.getContentType();
        if (!CONTENT_TYPES.contains(contentType)){   //不包含文件类型
            LOGGER.info("文件类型不合法:{}" , originalFilename);
            return null;
        }
        try {
            //校验文件内容
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (bufferedImage == null) {
                LOGGER.info("文件内容不合法:{}" , originalFilename);
                return null;
            }

            //保存到服务器
            file.transferTo(new File("D:\\tool\\images" + originalFilename));

            //返回url,进行回显
            return "http://image.imuster.top/" + originalFilename;
        } catch (IOException e) {
            LOGGER.info("服务器内部错误，{}文件上传失败！", originalFilename);
            e.printStackTrace();
        }
        return null;
    }
}
