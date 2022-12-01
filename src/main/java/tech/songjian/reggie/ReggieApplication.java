/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie
 * @className tech.songjian.reggie.ReggieApplication
 */
package tech.songjian.reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * ReggieApplication
 * @description
 * @author SongJian
 * @date 2022/12/1 17:02
 * @version
 */
@Slf4j
@SpringBootApplication
@ServletComponentScan
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class);
        log.info("项目启动成功....");
    }
}
 
