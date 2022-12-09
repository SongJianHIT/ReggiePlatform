/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service
 * @className tech.songjian.reggie.service.OrderService
 */
package tech.songjian.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.songjian.reggie.entity.Orders;

/**
 * OrderService
 * @description
 * @author SongJian
 * @date 2022/12/9 16:40
 * @version
 */
public interface OrderService extends IService<Orders> {

    /**
     * @title submit
     * @author SongJian
     * @param: orders
     * @updateTime 2022/12/9 16:50
     * @throws
     * @description 用户下单
     */
    public void submit(Orders orders);
}