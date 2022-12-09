/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.mapper
 * @className tech.songjian.reggie.mapper.OrderDetailMapper
 */
package tech.songjian.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.songjian.reggie.entity.OrderDetail;

/**
 * OrderDetailMapper
 * @description
 * @author SongJian
 * @date 2022/12/9 16:39
 * @version
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}