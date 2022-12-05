/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.common
 * @className tech.songjian.reggie.common.CustomException
 */
package tech.songjian.reggie.common;

/**
 * CustomException
 * @description 自定义业务异常
 * @author SongJian
 * @date 2022/12/5 19:31
 * @version
 */
public class CustomException extends RuntimeException{

    public CustomException (String message) {
         super(message);
    }
}