/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.config
 * @className tech.songjian.reggie.config.MybatisPlusConfig
 */
package tech.songjian.reggie.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlusConfig
 * @description 配置mp分页插件
 * @author SongJian
 * @date 2022/12/2 22:48
 * @version
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * MP配置分页拦截器
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
        mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return mybatisPlusInterceptor;
    }
}
 
