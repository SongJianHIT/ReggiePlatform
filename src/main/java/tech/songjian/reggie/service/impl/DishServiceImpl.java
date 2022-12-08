/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.DishServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.songjian.reggie.common.CustomException;
import tech.songjian.reggie.dto.DishDto;
import tech.songjian.reggie.entity.Dish;
import tech.songjian.reggie.entity.DishFlavor;
import tech.songjian.reggie.mapper.DishMapper;
import tech.songjian.reggie.service.DishFlavorService;
import tech.songjian.reggie.service.DishService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DishServiceImpl
 * @description
 * @author SongJian
 * @date 2022/12/5 19:19
 * @version
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * @param dishDto
     * @throws
     * @title
     * @author SongJian
     * @updateTime 2022/12/6 16:28
     * @description 新增菜品，同时插入菜品对应的口味数据，需要操作两张表
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveWithFlavor(DishDto dishDto) {

        // 保存菜品的基本信息到菜品表 dish
        this.save(dishDto);

        // 获取菜品id
        Long dishId = dishDto.getId();

        // 保存菜品口味数据到菜品口味表 dishflavor
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map((item) -> {
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * @param id
     * @throws
     * @title getByIdWithFlavor
     * @author SongJian
     * @param: id
     * @updateTime 2022/12/6 19:27
     * @return: tech.songjian.reggie.dto.DishDto
     * @description 根据id查询菜品信息和口味信息
     */
    public DishDto getByIdWithFlavor(Long id) {
        // 查询菜品基本信息 dish
        Dish dish = this.getById(id);

        // 查询菜品的口味信息 dish_flavor
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DishFlavor::getDishId, dish.getId());
        List<DishFlavor> list = dishFlavorService.list(lambdaQueryWrapper);

        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        dishDto.setFlavors(list);
        return dishDto;
    }

    /**
     * @param dishDto
     * @throws
     * @title updateWithFlavor
     * @author SongJian
     * @param: dishDto
     * @updateTime 2022/12/6 19:38
     * @description 更新菜品信息和口味信息
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateWithFlavor(DishDto dishDto) {
        // 更新 dish 表中的基本信息
        this.updateById(dishDto);
        // 清理当前菜品对应的口味数据 dish_flavor 的 delete 操作
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(lambdaQueryWrapper);

        // 添加当前提交片过来的口味数据 dish_flavor 的 Insert 操作
        List<DishFlavor> flavors = dishDto.getFlavors();
        // 添加 dishId
        flavors = flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());

        dishFlavorService.saveBatch(flavors);
    }

    /**
     * @param ids
     * @throws
     * @title updateStop
     * @author SongJian
     * @param: ids
     * @updateTime 2022/12/7 20:08
     * @description 菜品停售
     */
    public void updateStop(List<Long> ids) {
        // 根据ids查询
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Dish::getId, ids);
        List<Dish> dishes = this.list(lambdaQueryWrapper);

        // 设置停售状态
        dishes = dishes.stream().map((item)->{
            // 修改状态
            item.setStatus(0);
            return item;
        }).collect(Collectors.toList());

        // 更新数据
        this.updateBatchById(dishes);
    }

    /**
     * @param ids
     * @throws
     * @title updateStart
     * @author SongJian
     * @param: ids
     * @updateTime 2022/12/7 20:25
     * @description 菜品起售
     */
    public void updateStart(List<Long> ids) {
        // 根据ids查询
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Dish::getId, ids);
        List<Dish> dishes = this.list(lambdaQueryWrapper);

        // 设置起售状态
        dishes = dishes.stream().map((item)->{
            // 修改状态
            item.setStatus(1);
            return item;
        }).collect(Collectors.toList());

        // 更新数据
        this.updateBatchById(dishes);
    }

    /**
     * @param ids
     * @throws
     * @title remove
     * @author SongJian
     * @param: ids
     * @updateTime 2022/12/7 20:32
     * @description 删除菜品
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeWithFlavor(List<Long> ids) {
        // 1、根据id查询，检查是否起售
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 设定查询条件
        lambdaQueryWrapper.in(Dish::getId, ids).eq(Dish::getStatus, 1);
        int count = this.count(lambdaQueryWrapper);
        // 2、正在起售的菜品无法删除，抛出业务异常
        if (count > 0) {
            throw new CustomException("菜品正在售卖中，不能删除");
        }
        // 3、能够正常删除，dish表删除菜品
        this.removeByIds(ids);
        // dish_flavor表删除口味
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper1.in(DishFlavor::getDishId, ids);
        dishFlavorService.remove(lambdaQueryWrapper1);
    }
}
 
