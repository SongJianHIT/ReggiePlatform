/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.common
 * @className tech.songjian.reggie.common.R
 */
package tech.songjian.reggie.common;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * R
 * @description 返回结果封装类，服务端的响应数据最终都会被封装成此对象
 * @author SongJian
 * @date 2022/12/1 20:34
 * @version
 */
@Data
public class R<T> {

    /**
     * 编码：
     *     - 1 ：成功
     *     - 0（或其他） ：失败
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 动态数据
     */
    private Map map = new HashMap<>();
    
    /**
     * @title success
     * @author SongJian 
     * @param: object 返回数据
     * @updateTime 2022/12/1 20:39 
     * @return: tech.songjian.reggie.common.R<T>
     * @throws
     * @description 响应成功时，返回的R对象。直接 R.success(object) 进行调用
     */
    public static <T> R<T> success(T object) {
        R<T> r = new R<T>();
        r.data = object;
        r.code = 1;
        return r;
    }

    /**
     * @title error
     * @author SongJian
     * @param: String 错误信息
     * @updateTime 2022/12/1 20:40
     * @return: tech.songjian.reggie.common.R<T>
     * @throws
     * @description 响应失败时，返回的R对象。直接 R.error(msg) 进行调用
     */
    public static <T> R<T> error(String msg) {
        R r = new R();
        r.msg = msg;
        r.code = 0;
        return r;
    }
}
 
