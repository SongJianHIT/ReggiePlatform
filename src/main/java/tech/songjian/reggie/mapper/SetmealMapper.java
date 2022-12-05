/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.mapper
 * @className tech.songjian.reggie.mapper.SetmealMapper
 */
package tech.songjian.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.songjian.reggie.entity.Setmeal;

/**
 * SetmealMapper
 * @description 套餐mapper
 * @author SongJian
 * @date 2022/12/5 19:17
 * @version
 */
@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {
}