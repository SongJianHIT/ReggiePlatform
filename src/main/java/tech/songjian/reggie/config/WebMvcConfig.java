/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.config
 * @className tech.songjian.reggie.config.WebMvcConfig
 */
package tech.songjian.reggie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

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
}
 
