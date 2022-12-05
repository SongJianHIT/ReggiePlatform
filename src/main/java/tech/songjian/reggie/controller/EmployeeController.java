/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.controller
 * @className tech.songjian.reggie.controller.EmployeeController
 */
package tech.songjian.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
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

    /**
     * @title logout
     * @author SongJian
     * @param: request
     * @updateTime 2022/12/2 23:29
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 用户退出登入
     */
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

    /**
     * @title page
     * @author SongJian
     * @param: page
     * @param: pageSize
     * @param: name
     * @updateTime 2022/12/2 23:11
     * @return: tech.songjian.reggie.common.R<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
     * @throws
     * @description 分页查询员工，支持通过姓名模糊查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page={}, pageSize={}, name={}", page, pageSize, name);

        // 1、构造分页构造器
        Page pageInfo = new Page(page, pageSize);

        // 2、条件构造器
        LambdaQueryWrapper<Employee> lambdaQueryWrapper = new LambdaQueryWrapper();
        // name 不为空，才会添加条件
        lambdaQueryWrapper.like(name != null, Employee::getName, name);

        // 3、添加排序条件
        lambdaQueryWrapper.orderByDesc(Employee::getUpdateTime);

        // 4、执行查询
        employeeService.page(pageInfo, lambdaQueryWrapper);

        return R.success(pageInfo);
    }

    /**
     * @title update
     * @author SongJian
     * @param: employee
     * @updateTime 2022/12/3 17:39
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 根据员工id修改员工信息
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee) {
        log.info(employee.toString());
        // 更新日期和更新人
        employee.setUpdateTime(LocalDateTime.now());
        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));
        employeeService.updateById(employee);
        return R.success("员工信息修改成功");
    }

    /**
     * @title getById
     * @author SongJian
     * @param: id
     * @updateTime 2022/12/5 14:43
     * @return: tech.songjian.reggie.common.R<tech.songjian.reggie.entity.Employee>
     * @throws
     * @description 根据id查询员工信息
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id) {
        log.info("根据id查询员工信息...");
        Employee employee = employeeService.getById(id);
        return employee != null ? R.success(employee) : R.error("没有查询到对应员工信息");
    }
}
 
