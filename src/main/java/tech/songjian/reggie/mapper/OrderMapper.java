/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.mapper
 * @className tech.songjian.reggie.mapper.OrderMapper
 */
package tech.songjian.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.songjian.reggie.entity.Orders;

/**
 * OrderMapper
 * @description
 * @author SongJian
 * @date 2022/12/9 16:38
 * @version
 */
@Mapper
public interface OrderMapper extends BaseMapper<Orders> {
}
 
