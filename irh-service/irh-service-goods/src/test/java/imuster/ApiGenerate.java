package imuster;



import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

/**
 * @ClassName: ApiGenerate
 * @Description: 生成user模块的api文档，在使用之前需要先启动项目
 * @author: hmr
 * @date: 2020/1/9 10:51
 */
public class ApiGenerate {
    public static void main(String[] args) throws MalformedURLException {
        // 输出Ascii格式
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder().withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH).withPathsGroupedBy(GroupBy.TAGS).withGeneratedExamples()
                .withoutInlineSchema().build();

        Swagger2MarkupConverter.from(new URL("http://localhost:10400/v2/api-docs")).withConfig(config)
                .build().toFolder(Paths.get("src/docs/asciidoc/generated"));
    }

    /**
     * @Description: 生成md文件格式的api文档
     * @Author: hmr
     * @Date: 2020/1/9 10:53
     * @param
     * @reture: void
     **/
    public static void generateMd() throws Exception {
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:10400/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("./docs/markdown/goods"));
    }

    /**
     * @Description: 生成cf格式的
     * @Author: hmr
     * @Date: 2020/1/9 10:54
     * @param
     * @reture: void
     **/
    public static void generateCf() throws MalformedURLException {
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.CONFLUENCE_MARKUP)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL("http://localhost:8081/v2/api-docs"))
                .withConfig(config)
                .build()
                .toFolder(Paths.get("./docs/confluence/goods"));
    }


}
