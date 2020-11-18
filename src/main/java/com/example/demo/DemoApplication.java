package com.example.demo;

import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisReactiveAutoConfiguration;
import tk.mybatis.spring.annotation.MapperScan;
/*import org.mybatis.spring.annotation.MapperScan;

 * 导包报错
 * Caused by: org.apache.ibatis.builder.BuilderException:
   Error invoking SqlProvider method (tk.mybatis.mapper.provider.base.BaseSelectProvider.dynamicSQL).
 * Cause: java.lang.InstantiationException: tk.mybatis.mapper.provider.base.BaseSelectProvider
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//去除autoredisconfig的配置
//@EnableAutoConfiguration(exclude = {RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class})
@SpringBootApplication(exclude = {RedisAutoConfiguration.class, RedisReactiveAutoConfiguration.class})
@MapperScan("com.example.demo.mapper")
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

        /**
         * public static void main(String[] args) {
         DocsConfig config = new DocsConfig();
         //C:\Users\11780\Downloads\demo
         config.setProjectPath("C:\\Users\\11780\\Downloads\\demo"); // 项目根目录
         config.setProjectName("Demo1"); // 项目名称
         config.setApiVersion("V3.0");       // 声明该API的版本
         //docsPath： 文档输出目录（非必须，默认是${projectPath}/apidocs）
         //config.setDocsPath("your api docs path"); // 生成API 文档所在目录
         config.setAutoGenerate(Boolean.TRUE);  // 配置自动生成
         Docs.buildHtmlDocs(config); // 执行生成文档
         }
         }**/
    /**
     *
     */
}
