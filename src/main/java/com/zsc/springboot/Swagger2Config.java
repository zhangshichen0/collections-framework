package com.zsc.springboot;

import com.zsc.springboot.commons.Result;
import com.zsc.springboot.commons.swagger2.SwaggerParamType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shichen
 * @create 2018-12-11
 * @desc
 */
@Configuration
@EnableSwagger2
@Profile("swagger")
public class Swagger2Config {

    /**
     * swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
     */
    @Bean
    public Docket createRestApi() {

        //增加全局参数设置
        ParameterBuilder parameterBuilder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        parameterBuilder.name("Authentication")
                .description("token")
                .modelRef(new ModelRef("string"))
                .parameterType(SwaggerParamType.HEADER)
                .defaultValue("1")
                .required(true)
                .build();
        parameters.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .genericModelSubstitutes(Result.class)
                .globalOperationParameters(parameters)
                .useDefaultResponseMessages(false)
                .pathMapping("/")
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.zsc.springboot.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("集合各种框架项目")
                //创建人
                .contact(new Contact("张士晨", "https://github.com/zhangshichen0/collections-framework", ""))
                //版本号
                .version("0.1")
                //描述
                .description("API 描述")
                .build();
    }

}
