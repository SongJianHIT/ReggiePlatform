/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.common
 * @className tech.songjian.reggie.common.GlobalExceptionHandler
 */
package tech.songjian.reggie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * GlobalExceptionHandler
 * @description 全局异常处理器
 * @author SongJian
 * @date 2022/12/1 23:02
 * @version
 */
@ControllerAdvice(annotations = {RestController.class, Controller.class})
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * @title exceptionHandler
     * @author SongJian
     * @updateTime 2022/12/1 23:05
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 异常处理方法：SQLIntegrityConstraintViolationException
     */

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        log.error(ex.getMessage());
        // 判断是否是重复添加相同用户名
        if (ex.getMessage().contains("Duplicate entry")) {
            String[] s = ex.getMessage().split(" ");
            String msg = s[2]+"已存在";
            return R.error(msg);
        }
        return R.error("未知错误");
    }
    
    /**
     * @title exceptionHandler
     * @author SongJian 
     * @param: ex
     * @updateTime 2022/12/5 19:34 
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 全局捕获自定义业务异常
     */
    @ExceptionHandler(CustomException.class)
    public R<String> exceptionHandler(CustomException ex){
        log.error(ex.getMessage());
        return R.error(ex.getMessage());
    }
}
 
