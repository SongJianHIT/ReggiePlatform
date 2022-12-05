/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.common
 * @className tech.songjian.reggie.common.BaseContext
 */
package tech.songjian.reggie.common;

/**
 * BaseContext
 * @description 基于ThreadLocal封装工具类，用于保存和获取当前登入用户的id
 * @author SongJian
 * @date 2022/12/5 15:49
 * @version
 */
public class BaseContext {
    public static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * @title setCurrentId
     * @author SongJian
     * @param: id
     * @updateTime 2022/12/5 15:56
     * @throws
     * @description 设置值
     */
    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    /**
     * @title getCurrentId
     * @author SongJian
     * @updateTime 2022/12/5 15:57
     * @return: java.lang.Long
     * @throws
     * @description 获取值
     */
    public static Long getCurrentId() {
        return threadLocal.get();
    }
}
 
