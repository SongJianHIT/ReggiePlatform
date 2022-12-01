/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.EmployeeServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.songjian.reggie.entity.Employee;
import tech.songjian.reggie.mapper.EmployeeMapper;
import tech.songjian.reggie.service.EmployeeService;

/**
 * EmployeeServiceImpl
 * @description
 * @author SongJian
 * @date 2022/12/1 20:23
 * @version
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
 
