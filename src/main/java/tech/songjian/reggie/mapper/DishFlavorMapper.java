/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.mapper
 * @className tech.songjian.reggie.mapper.DishFlavorMapper
 */
package tech.songjian.reggie.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.songjian.reggie.entity.DishFlavor;

/**
 * DishFlavorMapper
 * @description DishFlavorMapper
 * @author SongJian
 * @date 2022/12/6 15:51
 * @version
 */
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {
}