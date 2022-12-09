/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.controller
 * @className tech.songjian.reggie.controller.ShoppingCartController
 */
package tech.songjian.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.songjian.reggie.common.BaseContext;
import tech.songjian.reggie.common.R;
import tech.songjian.reggie.entity.ShoppingCart;
import tech.songjian.reggie.service.ShoppingCartService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ShoppingCartController
 * @description
 * @author SongJian
 * @date 2022/12/9 10:33
 * @version
 */
@RestController
@Slf4j
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * @title add
     * @author SongJian
     * @param: shoppingCart
     * @updateTime 2022/12/9 10:35
     * @return: tech.songjian.reggie.common.R<tech.songjian.reggie.entity.ShoppingCart>
     * @throws
     * @description 添加购物车
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("添加购物车：{}", shoppingCart);
        // 设置用户id，制定是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        // 查询当前菜品或者套餐是否存在于购物车中
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, currentId);
        if (dishId != null) {
            // 添加到购物车的是菜品
            lambdaQueryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            // 添加到购物车的是套餐
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        ShoppingCart shoppingCart1 = shoppingCartService.getOne(lambdaQueryWrapper);
        if (shoppingCart1 != null) {
            // 如果已经存在，则在原来数量基础上加一
            shoppingCart1.setNumber(shoppingCart1.getNumber() + 1);
            shoppingCartService.updateById(shoppingCart1);
        } else {
            // 如果不存在，则添加到购物车中，数量默认是一
            shoppingCart.setNumber(1);
            shoppingCart.setCreateTime(LocalDateTime.now());
            shoppingCartService.save(shoppingCart);
            shoppingCart1 = shoppingCart;
        }
        return R.success(shoppingCart1);
    }

    @GetMapping("/list")
    public R<List<ShoppingCart>> list() {
        log.info("查询购物车...");
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        lambdaQueryWrapper.orderByAsc(ShoppingCart::getCreateTime);
        List<ShoppingCart> list = shoppingCartService.list(lambdaQueryWrapper);
        return R.success(list);
    }

    @DeleteMapping("/clean")
    public R<String> clean() {
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, BaseContext.getCurrentId());
        shoppingCartService.remove(lambdaQueryWrapper);
        return R.success("清空购物车成功");
    }

    @PostMapping("/sub")
    public R<String> delete(@RequestBody ShoppingCart shoppingCart) {
        log.info("正在删除购物车：{}", shoppingCart);
        // 设置用户id，制定是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        // 查询当前菜品或者套餐是否存在于购物车中
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ShoppingCart::getUserId, currentId);
        if (dishId != null) {
            // 删除购物车的是菜品
            lambdaQueryWrapper.eq(ShoppingCart::getDishId, dishId);
        } else {
            // 删除购物车的是套餐
            lambdaQueryWrapper.eq(ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        }
        ShoppingCart shoppingCart1 = shoppingCartService.getOne(lambdaQueryWrapper);
        if (shoppingCart1 != null) {
            if (shoppingCart1.getNumber() > 1) {
                // 如果已经存在多份，则在原来数量基础上减一
                shoppingCart1.setNumber(shoppingCart1.getNumber() - 1);
                shoppingCartService.updateById(shoppingCart1);
            } else {
                // 如果只有一份，直接删除
                shoppingCartService.removeById(shoppingCart1);
            }
        }
        return R.success("删除成功");
    }
}
 
