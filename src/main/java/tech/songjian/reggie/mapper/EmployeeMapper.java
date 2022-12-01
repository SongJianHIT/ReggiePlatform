/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.mapper
 * @className tech.songjian.reggie.mapper.EmployeeMapper
 */
package tech.songjian.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.songjian.reggie.entity.Employee;

/**
 * EmployeeMapper
 * @description 员工mapper
 * @author SongJian
 * @date 2022/12/1 20:19
 * @version
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}