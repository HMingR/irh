package top.imuster;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.file.provider.IrhFileApplication;
import top.imuster.file.provider.web.controller.FileController;

import javax.annotation.Resource;

/**
 * @ClassName: test
 * @Description: TODO
 * @author: hmr
 * @date: 2020/2/20 10:22
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IrhFileApplication.class)
public class test {

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    @Test
    public void test(){
        String test = fileServiceFeignApi.test();
        System.out.println(test);
    }
}
