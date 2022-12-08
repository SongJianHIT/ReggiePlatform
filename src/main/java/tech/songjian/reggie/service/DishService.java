/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service
 * @className tech.songjian.reggie.service.DishService
 */
package tech.songjian.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.songjian.reggie.dto.DishDto;
import tech.songjian.reggie.entity.Dish;

import java.util.List;

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

    /**
     * @title getByIdWithFlavor
     * @author SongJian
     * @param: id
     * @updateTime 2022/12/6 19:27
     * @return: tech.songjian.reggie.dto.DishDto
     * @throws
     * @description 根据id查询菜品信息和口味信息
     */
    public DishDto getByIdWithFlavor(Long id);


    /**
     * @title updateWithFlavor
     * @author SongJian
     * @param: dishDto
     * @updateTime 2022/12/6 19:38
     * @throws
     * @description 更新菜品信息和口味信息
     */
    public void updateWithFlavor(DishDto dishDto);


    /**
     * @title updateStop
     * @author SongJian
     * @param: ids
     * @updateTime 2022/12/7 20:08
     * @throws
     * @description 菜品停售
     */
    public void updateStop(List<Long> ids);

    /**
     * @title updateStart
     * @author SongJian
     * @param: ids
     * @updateTime 2022/12/7 20:25
     * @throws
     * @description 菜品起售
     */
    public void updateStart(List<Long> ids);

    /**
     * @title remove
     * @author SongJian
     * @param: ids
     * @updateTime 2022/12/7 20:32
     * @throws
     * @description 删除菜品
     */
    public void removeWithFlavor(List<Long> ids);
}