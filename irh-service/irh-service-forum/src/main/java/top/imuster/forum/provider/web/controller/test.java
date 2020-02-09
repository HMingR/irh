package top.imuster.forum.provider.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.imuster.forum.api.pojo.ArticleInfo;
import top.imuster.forum.provider.service.ReWriteArticleDetailPage;

import javax.annotation.Resource;

/**
 * @ClassName: test
 * @Description: TODO
 * @author: hmr
 * @date: 2020/2/9 15:01
 */
@Controller
@RequestMapping("/test")
public class test {

    @Resource
    ReWriteArticleDetailPage reWriteArticleDetailPage;

    @GetMapping
    public void index(Model model){
        /*ArticleInfo articleInfo = new ArticleInfo();
        articleInfo.setId(12341234l);
        articleInfo.setTitle("测试");
        model.addAttribute("product", articleInfo);
        return "test";*/
        reWriteArticleDetailPage.createHtml(new ArticleInfo());
    }

    @GetMapping("/1")
    @ResponseBody
    public String test1(){
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                "    <title>Document</title>\n" +
                "    <!-- 引入样式 -->\n" +
                "    <link rel=\"stylesheet\" href=\"https://unpkg.com/element-ui/lib/theme-chalk/index.css\">\n" +
                "    <!-- 引入字体图标 -->\n" +
                "    <link rel=\"stylesheet\" href=\"./css/font/iconfont.css\">\n" +
                "    <style>\n" +
                "        #app {\n" +
                "            width: 1200px;\n" +
                "            margin: 0 auto;\n" +
                "        }\n" +
                "\n" +
                "        .el-tabs__item.is-active {\n" +
                "            color: #eb2f06;\n" +
                "        }\n" +
                "\n" +
                "        .el-tabs__active-bar {\n" +
                "            background-color: #eb2f06;\n" +
                "        }\n" +
                "\n" +
                "        .el-tabs__item:hover {\n" +
                "            color: #eb2f06;\n" +
                "        }\n" +
                "\n" +
                "        /* main */\n" +
                "        .main{\n" +
                "            display: flex;\n" +
                "            flex-flow: nowrap;\n" +
                "        }\n" +
                "        .text_description{\n" +
                "            margin-left: 20px;\n" +
                "        }\n" +
                "        .text_description>p:nth-child(1){\n" +
                "            font-size: 16px;\n" +
                "            line-height: 28px;\n" +
                "            color: #666;\n" +
                "            font-style: normal;\n" +
                "            font-weight: 700;\n" +
                "        }\n" +
                "        .text_description>p:nth-child(2),.text_description>p:nth-child(3){\n" +
                "            font-size: 12px;\n" +
                "            line-height: 20px;\n" +
                "            font-weight: 400;\n" +
                "            font-style: normal;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .text_description>p>a{\n" +
                "            text-decoration: none;\n" +
                "            font-size: 12px;\n" +
                "            line-height: 20px;\n" +
                "            font-weight: 400;\n" +
                "            font-style: normal;\n" +
                "            color: #6486CC;\n" +
                "        }\n" +
                "        .text_description>p>a:hover{\n" +
                "            color: #eb2f06;\n" +
                "        }\n" +
                "        .el-button{\n" +
                "            font-style: normal;\n" +
                "            font-weight: 700;\n" +
                "        }\n" +
                "\n" +
                "        .el-button:focus, .el-button:hover{\n" +
                "            color: #fff;\n" +
                "            border-color: #eb2f06;\n" +
                "            background-color: #eb2f06;\n" +
                "        }\n" +
                "\n" +
                "\n" +
                "        /* container_footer */\n" +
                "        .container_footer{\n" +
                "            font-style: normal;\n" +
                "            font-weight: 400;\n" +
                "            font-size: 12px;\n" +
                "            line-height: 18px;\n" +
                "            color: #666;\n" +
                "        }\n" +
                "\n" +
                "        /* .el-card__body */\n" +
                "        .el-card__body{\n" +
                "            padding: 10px;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<div id=\"app\">\n" +
                "    <div class=\"header\">\n" +
                "        <el-row style=\"height: 60px; overflow: hidden; font-size: 40px; line-height: 60px;\">\n" +
                "            <el-col :span=\"12\">\n" +
                "                <img src=\"./images/LOGO2.png\" alt=\"\" style=\"height: 60px;\">\n" +
                "            </el-col>\n" +
                "            <el-col :span=\"12\" style=\"display: flex; justify-content: flex-end; font-size: 12px;\">\n" +
                "                <span class=\"iconfont icon-weizhi\" style=\"font-size: 24px;\"></span><span>位置信息</span>\n" +
                "            </el-col>\n" +
                "        </el-row>\n" +
                "    </div>\n" +
                "    <!-- 商品内容区域 -->\n" +
                "    <div class=\"main\" style=\"margin: 6px 0px;\">\n" +
                "        <div style=\"overflow:hidden;height: 350px; width: 350px; background-color: #999;\">\n" +
                "            <img src=\"./images/shoe.jpg\" alt=\"\" style=\"width: 350px;height: 350px;\">\n" +
                "        </div>\n" +
                "        <div class=\"text_description\" style=\"width: 600px;\">\n" +
                "            <p>探巡 鞋子男潮流男鞋手工定制2019冬季新款加绒保暖高帮老爹鞋男时尚百搭韩版运动休闲鞋板鞋 A006-灰黑探巡 </p>\n" +
                "            <p>服务支持: <a href=\"#\">包邮</i> <a href=\"#\">闪电退款</a> <a href=\"#\">店家实名</a> <a href=\"#\">平台担保</a></p>\n" +
                "            <p>交易方式: <a href=\"#\">支持当面交易</a> <a href=\"#\">支持包邮</a></p>\n" +
                "            <el-button @click=\"\" style=\"margin-top: 30px;\">立即购买 &raquo;</el-button>\n" +
                "        </div>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "    <!-- 详情区域 -->\n" +
                "    <div class=\"container_footer\" style=\"margin-top: 20px;\">\n" +
                "        <el-tabs v-model=\"activeName\" @tab-click=\"handleClick\">\n" +
                "            <el-tab-pane label=\"商品介绍\" name=\"first\">\n" +
                "                <p>品牌: <span>匡威</span></p>\n" +
                "                <el-row>\n" +
                "                    <el-col :span=\"6\">商品名称:<span>测试看看</span></el-col>\n" +
                "                    <el-col :span=\"6\">商品编号:<span>123123</span></el-col>\n" +
                "                    <el-col :span=\"6\">商品颜色:<span>测试看看</span></el-col>\n" +
                "                    <el-col :span=\"6\">商品尺码:<span>测试看看</span></el-col>\n" +
                "                </el-row>\n" +
                "                <el-row>\n" +
                "                    <el-col :span=\"6\">入手日期:<span>测试看看</span></el-col>\n" +
                "                    <el-col :span=\"6\">新旧程度:<span>测试看看</span></el-col>\n" +
                "                    <el-col></el-col>\n" +
                "                    <el-col></el-col>\n" +
                "                </el-row>\n" +
                "                <el-divider></el-divider>\n" +
                "                <el-row>\n" +
                "                    <!-- 这个地方放图片文字介绍之类的 -->\n" +
                "                    <img src=\"//img10.360buyimg.com/imgzone/jfs/t1/99628/26/7916/676796/5dff8da5Ea4a67663/2e4d65d342440c79.jpg\" alt=\"\" style=\"max-width: 1000px;\">\n" +
                "                </el-row>\n" +
                "            </el-tab-pane>\n" +
                "            <el-tab-pane label=\"卖家动态\" name=\"second\">\n" +
                "                <el-card shadow='never'>\n" +
                "                    <el-row>\n" +
                "                        <el-col :span=\"1\" style=\"height: 60px;\">\n" +
                "                            <img src=\"./images/shoe.jpg\" alt=\"\" style=\"width: 60px;height: 60px;\">\n" +
                "                        </el-col>\n" +
                "                        <el-col :span=\"18\" style=\"margin-left: 20px; font-size: 16px;\">\n" +
                "                            <p>2019年12月28日上架了一个新的宝贝</p>\n" +
                "                        </el-col>\n" +
                "                        <el-col :span=\"4\" style=\"font-size: 12px; color: #eb2f06;font-weight: 400;line-height: 20px;margin-top: 6px;\"><p>￥<span style=\"font-size:16px;font-weight: 600; \">18.8</span></p></el-col>\n" +
                "                    </el-row>\n" +
                "                </el-card>\n" +
                "            </el-tab-pane>\n" +
                "            <el-tab-pane label=\"买家留言\" name=\"third\">\n" +
                "                <!-- 买家留言 -->\n" +
                "                <el-card>\n" +
                "                    <el-row>\n" +
                "                        <el-col :span='1' style=\"line-height: 60px;\">\n" +
                "                            <img src=\"//misc.360buyimg.com/user/myjd-2015/css/i/peisong.jpg\" alt=\"\" style=\"width: 60px;\">\n" +
                "                        </el-col>\n" +
                "                        <el-col :span=\"2\">\n" +
                "                            <span style=\"font-weight: 600; margin-left: 20px;\">渣渣辉</span>\n" +
                "                        </el-col>\n" +
                "                        <el-col :span=\"16\" style=\"margin:6px 0 0 -50px;\">\n" +
                "                            <p>只要写字设计特别的好看，非常的舒服，而且面料特别的好，非常的柔软，穿起来一点都不会觉得紧绷，也不会觉得很硬，穿起来特别的舒服，轻飘飘的感觉。</p>\n" +
                "                        </el-col>\n" +
                "                        <el-col :span=\"4\" style=\"margin-top:40px\"><p>2019-12-27 18:00</p></el-col>\n" +
                "                    </el-row>\n" +
                "                </el-card>\n" +
                "            </el-tab-pane>\n" +
                "        </el-tabs>\n" +
                "    </div>\n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "<script src=\"https://unpkg.com/vue/dist/vue.js\"></script>\n" +
                "<!-- 引入组件库 -->\n" +
                "<script src=\"https://unpkg.com/element-ui/lib/index.js\"></script>\n" +
                "<!-- 引入jquery -->\n" +
                "<script src=\"https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js\"></script>\n" +
                "\n" +
                "<!-- <script>\n" +
                "$.getJSON('./data.json',function(result){\n" +
                "  listObj = result;\n" +
                "  console.log(listObj);\n" +
                "})\n" +
                "</script> -->\n" +
                "\n" +
                "<script>\n" +
                "    new Vue({\n" +
                "        el: '#app',\n" +
                "        data () {\n" +
                "            return {\n" +
                "                activeName: 'first'\n" +
                "            }\n" +
                "        },\n" +
                "        methods: {\n" +
                "            handleClick(tab, event) {\n" +
                "                console.log(tab, event);\n" +
                "            }\n" +
                "        }\n" +
                "    })\n" +
                "</script>\n" +
                "\n" +
                "</html>";
    }
}
