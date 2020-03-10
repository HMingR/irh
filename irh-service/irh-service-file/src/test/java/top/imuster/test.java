package top.imuster;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.file.provider.IrhFileApplication;

import java.util.StringTokenizer;

/**
 * @ClassName: test
 * @Description: TODO
 * @author: hmr
 * @date: 2020/2/20 10:22
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(classes = IrhFileApplication.class)
public class test {
    public static void main(String[] args) {
        String [][]s={{"helloworld","hello world"},{"this is","a java program"}};
        System.out.println((new StringTokenizer(s[1][1])).countTokens()>2);
        System.out.println(1+2+"");
    }
}
