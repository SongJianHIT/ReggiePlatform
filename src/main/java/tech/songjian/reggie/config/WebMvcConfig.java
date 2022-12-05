/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.config
 * @className tech.songjian.reggie.config.WebMvcConfig
 */
package tech.songjian.reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
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
}
 
