/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.controller
 * @className tech.songjian.reggie.controller.OrderController
 */
package tech.songjian.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.songjian.reggie.common.BaseContext;
import tech.songjian.reggie.common.R;
import tech.songjian.reggie.entity.Orders;
import tech.songjian.reggie.service.OrderService;

import java.util.List;

/**
 * OrderController
 * @description
 * @author SongJian
 * @date 2022/12/9 16:44
 * @version
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * @title submit
     * @author SongJian
     * @param: orders
     * @updateTime 2022/12/9 22:53
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 用户下单功能
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders) {
        log.info("订单数据：{}", orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/userPage")
    public R<Page<Orders>> list(int page, int pageSize) {
        log.info("查询订单：{}, {}", page, pageSize);
        Long userId = BaseContext.getCurrentId();
        Page<Orders> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Orders::getUserId, userId);
        Page<Orders> page2 = orderService.page(page1, lambdaQueryWrapper);
        return R.success(page2);
    }
}
 
