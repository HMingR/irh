package top.imuster.life.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import top.imuster.file.api.service.FileServiceFeignApi;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.life.provider.service.ReWriteArticleDetailPage;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

/**
 * @ClassName: ReWriteArticleDetailPageImpl
 * @Description: ReWriteArticleDetailPageImpl
 * @author: hmr
 * @date: 2020/2/9 15:32
 */
@Service("reWriteArticleDetailPage")
public class ReWriteArticleDetailPageImpl implements ReWriteArticleDetailPage {

    private static final String destPath = "F:/alipay/static";

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    FileServiceFeignApi fileServiceFeignApi;

    @Override
    public void createHtml(ArticleInfo articleInfo) {
        HashMap<String, Object> res = new HashMap<>();
        res.put("title", "测试看看");
        res.put("id", 123123l);
        try{
            Context context = new Context();
            context.setVariables(res);
            File f = new File("F:/alipay/text.html");
            PrintWriter printWriter = new PrintWriter(f, "UTF-8");
            templateEngine.process("test", context, printWriter);
            String s = printWriter.toString();
            System.out.println(s);
            printWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }

    }
}
