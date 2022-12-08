/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.mapper
 * @className tech.songjian.reggie.mapper.UserMapper
 */
package tech.songjian.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.songjian.reggie.entity.User;

/**
 * UserMapper
 * @description
 * @author SongJian
 * @date 2022/12/7 21:34
 * @version
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}