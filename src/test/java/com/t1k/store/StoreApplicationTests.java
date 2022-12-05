package com.t1k.store;

import io.github.swagger2markup.*;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

@SpringBootTest
class StoreApplicationTests
{
    @Test
    void test()
    {
//        // 导出swagger文档为md
//        // 输出Markdown到单文件
//        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
//                .withMarkupLanguage(MarkupLanguage.MARKDOWN)
//                .withOutputLanguage(Language.ZH)
//                .withPathsGroupedBy(GroupBy.TAGS)
//                .withGeneratedExamples()
//                .withoutInlineSchema()
//                .build();
//        try
//        {
//            Swagger2MarkupConverter.from(new URL("http://localhost:8080/v2/api-docs"))
//                                   .withConfig(config)
//                                   .build()
//                                   .toFile(Paths.get("src/main/resources/docs/markdown"));
//        }
//        catch(MalformedURLException e)
//        {
//            e.printStackTrace();
//        }
    }
}
