/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.controller
 * @className tech.songjian.reggie.controller.EmployeeController
 */
package tech.songjian.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.songjian.reggie.common.R;
import tech.songjian.reggie.entity.Employee;
import tech.songjian.reggie.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * EmployeeController
 *
 * @author SongJian
 * @description 员工 controller
 * @date 2022/12/1 20:25
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * @throws
     * @title login
     * @author SongJian
     * @param: request 将员工对象id存储至session，可获取当前登入用户
     * @param: employee
     * @updateTime 2022/12/1 20:46
     * @return: tech.songjian.reggie.common.R<tech.songjian.reggie.entity.Employee>
     * @description 用户登入方法
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {

        // 1、密码进行 md5 加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2、根据用户名查数据库
        LambdaQueryWrapper<Employee> employeeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        employeeLambdaQueryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(employeeLambdaQueryWrapper);

        // 3、如果没有查询到，则返回登入失败结果
        if (emp == null) {
            return R.error("登入失败");
        }

        // 4、比对密码
        if (!emp.getPassword().equals(password)) {
            return R.error("密码错误");
        }

        // 5、查看员工状态是否可用
        if (emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

        // 6、将员工id放入session，返回成功信息
        request.getSession().setAttribute("employee", emp.getId());
        return R.success(emp);
    }

    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {

        // 1、清理Session中的用户id
        request.getSession().removeAttribute("employee");

        // 2、返回结果
        return R.success("退出成功");
    }

    /**
     * @throws
     * @title save
     * @author SongJian
     * @param: request
     * @param: employee
     * @updateTime 2022/12/1 22:47
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @description 新增员工
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        log.info("新增员工，员工信息：{}", employee.toString());

        // 设置初始密码，并加密
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        // 设置创建时间和更新时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        // 设置创建者和更新者
        // 首先获得当前登入用户id
        Long empId = (Long) request.getSession().getAttribute("employee");
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);

        employeeService.save(employee);
        return R.success("新增员工成功");
    }
}
 
