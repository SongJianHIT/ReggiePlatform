/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.config
 * @className tech.songjian.reggie.config.WebMvcConfig
 */
package tech.songjian.reggie.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tech.songjian.reggie.common.JacksonObjectMapper;

import java.util.List;

/**
 * WebMvcConfig
 * @description
 * @author SongJian
 * @date 2022/12/1 17:16
 * @version
 */
@Slf4j
@Configuration
@EnableSwagger2
@EnableKnife4j
public class WebMvcConfig extends WebMvcConfigurationSupport {

    /**
     * @title addResourceHandlers
     * @author SongJian
     * @param: registry
     * @updateTime 2022/12/1 17:17
     * @throws
     * @description 设置静态资源映射
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射....");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


    /**
     * @title extendMessageConverters
     * @author SongJian
     * @param: converters
     * @updateTime 2022/12/3 18:00
     * @throws
     * @description 扩展 MVC 的消息转换器
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        // 创建消息转换器对象
        // 将放回结果进行转换，扩展功能（Long -》 String）
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // 设置对象转换器，底层使用 Jackson 将 Java 对象转为 json
        converter.setObjectMapper(new JacksonObjectMapper());

        // 将上面的消息转换器对象追加到 MVC 框架的转换器上
        // 0：优先级最高，优先使用
        converters.add(0, converter);
        super.extendMessageConverters(converters);
    }

    @Bean
    public Docket createRestApi() {
        // 文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("tech.songjian.reggie.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("瑞吉外卖")
                .version("1.0")
                .description("瑞吉外卖接口文档")
                .build();
    }
}
 
