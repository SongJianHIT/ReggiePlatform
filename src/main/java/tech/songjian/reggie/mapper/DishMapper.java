/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.mapper
 * @className tech.songjian.reggie.mapper.DishMapper
 */
package tech.songjian.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.songjian.reggie.entity.Dish;

/**
 * DishMapper
 * @description 菜品mapper
 * @author SongJian
 * @date 2022/12/5 19:16
 * @version
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}