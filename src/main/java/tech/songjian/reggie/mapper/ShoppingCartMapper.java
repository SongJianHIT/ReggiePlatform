/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.mapper
 * @className tech.songjian.reggie.mapper.ShoppingCartMapper
 */
package tech.songjian.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.songjian.reggie.entity.ShoppingCart;

/**
 * ShoppingCartMapper
 * @description
 * @author SongJian
 * @date 2022/12/9 10:24
 * @version
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
 
