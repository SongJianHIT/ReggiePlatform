/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service
 * @className tech.songjian.reggie.service.DishService
 */
package tech.songjian.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.songjian.reggie.dto.DishDto;
import tech.songjian.reggie.entity.Dish;

/**
 * DishService
 * @description DishService
 * @author SongJian
 * @date 2022/12/5 19:18
 * @version
 */
public interface DishService extends IService<Dish> {

    /**
     * @title
     * @author SongJian
     * @updateTime 2022/12/6 16:28
     * @throws
     * @description 新增菜品，同时插入菜品对应的口味数据，需要操作两张表
     */
    public void saveWithFlavor(DishDto dishDto);

}