/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service
 * @className tech.songjian.reggie.service.StemealService
 */
package tech.songjian.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.songjian.reggie.dto.SetmealDto;
import tech.songjian.reggie.entity.Setmeal;

import java.util.List;

/**
 * StemealService
 * @description
 * @author SongJian
 * @date 2022/12/5 19:20
 * @version
 */
public interface SetmealService extends IService<Setmeal> {
    /**
     * @title saveWithDish
     * @author SongJian
     * @updateTime 2022/12/7 15:19
     * @throws
     * @description 新增套餐，同时保存套餐和菜品组合
     */
    public void saveWithDish(SetmealDto setmealDto);

    /**
     * @title removeWithDish
     * @author SongJian
     * @param: ids
     * @updateTime 2022/12/7 17:04
     * @throws
     * @description 删除套餐，同时删除套餐与菜品的关联数据
     */
    public void removeWithDish(List<Long> ids);
}
 
