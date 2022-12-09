/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.OrderServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.songjian.reggie.common.BaseContext;
import tech.songjian.reggie.common.CustomException;
import tech.songjian.reggie.entity.*;
import tech.songjian.reggie.mapper.OrderMapper;
import tech.songjian.reggie.service.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * OrderServiceImpl
 * @description
 * @author SongJian
 * @date 2022/12/9 16:41
 * @version
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * @param orders
     * @throws
     * @title submit
     * @author SongJian
     * @param: orders
     * @updateTime 2022/12/9 16:50
     * @description 用户下单
     */
    public void submit(Orders orders) {
        // 1、当前用户id
        Long currentId = BaseContext.getCurrentId();

        // 2、查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, currentId);
        List<ShoppingCart> carts = shoppingCartService.list(lambdaQueryWrapper);

        if (carts == null || carts.size() == 0) {
            throw new CustomException("购物车为空，无法下单");
        }

        // 查询用户数据
        User user = userService.getById(currentId);

        // 查询地址数据
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());

        if (addressBook == null) {
            throw new CustomException("用户地址信息有误，无法下单");
        }

        // 3、订单表插入数据，一条数据
        long orderId = IdWorker.getId();

        AtomicInteger amount = new AtomicInteger(0);
        List<OrderDetail> orderDetails = carts.stream().map((item)->{
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));
        orders.setUserId(currentId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));
        this.save(orders);

        // 4、订单明细表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);

        // 5、清空购物车
        shoppingCartService.remove(lambdaQueryWrapper);
    }
}
 
