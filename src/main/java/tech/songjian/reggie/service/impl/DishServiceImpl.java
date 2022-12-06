/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.DishServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
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
}
 
