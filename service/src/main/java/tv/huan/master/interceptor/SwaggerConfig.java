package tv.huan.master.interceptor;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2017/5/9 0009
 * Time: 13:39
 * To change this template use File | Settings | File Templates
 */
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket bill() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("client接口")
                .select()                       //选择哪些路径和API会生成document
                .paths(PathSelectors.ant("/client/**"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("Swagger2 构建RESTful API")
                // 创建人
                .contact(new Contact("warriorr", null, "warriorr@163.com"))
                // 版本号
                .version("1.0")
                // 描述
                .description("master").build();
    }
}
