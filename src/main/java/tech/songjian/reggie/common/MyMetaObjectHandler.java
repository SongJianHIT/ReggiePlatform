/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.common
 * @className tech.songjian.reggie.common.MyMetaObjectHandler
 */
package tech.songjian.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import tech.songjian.reggie.entity.Employee;

import java.time.LocalDateTime;

/**
 * MyMetaObjectHandler
 * @description 自定义元数据对象处理器
 * @author SongJian
 * @date 2022/12/5 15:17
 * @version
 */

@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * @title insertFill
     * @author SongJian
     * @param: metaObject
     * @updateTime 2022/12/5 15:23
     * @throws
     * @description 插入操作，自动填充字段
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    /**
     * @title updateFill
     * @author SongJian
     * @param: metaObject
     * @updateTime 2022/12/5 15:23
     * @throws
     * @description 修改操作，自动填充
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}
 
