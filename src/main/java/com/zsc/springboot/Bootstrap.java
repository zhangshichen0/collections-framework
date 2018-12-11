package com.zsc.springboot;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Profiles;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.filter.FormContentFilter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

/**
 * @author shichen
 * @create 2018-12-07
 * @desc
 */
@SpringBootApplication(scanBasePackages = "com.zsc.springboot")
@EnableConfigurationProperties
@EnableWebMvc
@EnableAspectJAutoProxy
@EnableScheduling
public class Bootstrap implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
    }

    /**
     * 增加消息转换器
     *
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        //删除所有支持application/json的converter
        for (int i = converters.size() - 1; i >=0; i ++) {
            HttpMessageConverter<?> httpMessageConverter = converters.get(i);
            List<MediaType> mediaTypes = httpMessageConverter.getSupportedMediaTypes();
            if (mediaTypes.contains(MediaType.APPLICATION_JSON) || mediaTypes.contains(MediaType.APPLICATION_JSON_UTF8)) {
                converters.remove(i);
            }
        }
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = fastJsonHttpMessageConverter.getFastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero);

        fastJsonHttpMessageConverter.setSupportedMediaTypes(Lists.newArrayList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8));
        converters.add(fastJsonHttpMessageConverter);
    }

    /**
     * 增加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    /**
     * 加载资源文件
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //swagger静态页面和样式加载，只在激活的profile包含swagger时，才加载资源文件
        if (applicationContext.getEnvironment().acceptsProfiles(Profiles.of("swagger"))) {
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    @Bean
    public FormContentFilter httpPutFormContentFilter() {
        return new FormContentFilter();
    }

    /**
     * 配置跨域请求相关参数
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/collections-framework/api/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedMethods("*")
                .maxAge(3600);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
