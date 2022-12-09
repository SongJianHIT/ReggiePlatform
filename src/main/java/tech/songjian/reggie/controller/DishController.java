/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.controller
 * @className tech.songjian.reggie.controller.DishController
 */
package tech.songjian.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.songjian.reggie.common.R;
import tech.songjian.reggie.dto.DishDto;
import tech.songjian.reggie.entity.Category;
import tech.songjian.reggie.entity.Dish;
import tech.songjian.reggie.entity.DishFlavor;
import tech.songjian.reggie.service.CategoryService;
import tech.songjian.reggie.service.DishFlavorService;
import tech.songjian.reggie.service.DishService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DishController
 * @description 菜品管理
 * @author SongJian
 * @date 2022/12/6 15:54
 * @version
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    /**
     * @title save
     * @author SongJian
     * @param: dishDto
     * @updateTime 2022/12/6 16:22
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 新增菜品
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        log.info(dishDto.toString());
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * @title page
     * @author SongJian
     * @param: page
     * @param: pageSize
     * @param: name
     * @updateTime 2022/12/6 17:07
     * @return: tech.songjian.reggie.common.R<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
     * @throws
     * @description 菜品信息分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        // 创建分页构造器对象
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> pageDtoInfo = new Page<>();

        // 条件构造器
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null, Dish::getName, name);
        lambdaQueryWrapper.orderByDesc(Dish::getUpdateTime);

        // 执行分页查询
        dishService.page(pageInfo, lambdaQueryWrapper);

        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, pageDtoInfo, "records");

        // 获取查询结果
        List<Dish> records = pageInfo.getRecords();

        // 处理成 Dto List 集合
        List<DishDto> list = records.stream().map((item)->{
            DishDto dishDto = new DishDto();
            // 对象拷贝
            BeanUtils.copyProperties(item, dishDto);
            // 每个菜品的分类id
            Long categoryId = item.getCategoryId();
            // 根据 id 查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        pageDtoInfo.setRecords(list);
        return R.success(pageDtoInfo);
    }

    /**
     * @title get
     * @author SongJian
     * @param: id
     * @updateTime 2022/12/6 19:25
     * @return: tech.songjian.reggie.common.R<tech.songjian.reggie.dto.DishDto>
     * @throws
     * @description 查询菜品信息和对应的口味信息
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id) {
        DishDto dishDto = dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * @title update
     * @author SongJian
     * @updateTime 2022/12/6 19:37
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 修改菜品
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {
        dishService.updateWithFlavor(dishDto);
        return R.success("修改菜品成功");
    }

    /**
     * @title list
     * @author SongJian
     * @param: dish
     * @updateTime 2022/12/7 14:46
     * @return: tech.songjian.reggie.common.R<java.util.List<tech.songjian.reggie.entity.Dish>>
     * @throws
     * @description 根据条件查询菜品数据列表
     */
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish) {
//        // 构造查询条件对象
//        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
//        lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
//        // 添加排序条件
//        lambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
//        List<Dish> list = dishService.list(lambdaQueryWrapper);
//        return R.success(list);
//    }
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish) {
        // 构造查询条件对象
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId());
        // 添加排序条件
        lambdaQueryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(lambdaQueryWrapper);

        List<DishDto> res = list.stream().map((item)->{
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();
            // 根据 id 查询分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }

            // 当前菜品id
            Long dishId = item.getId();
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper1 = new LambdaQueryWrapper<>();
            lambdaQueryWrapper1.eq(DishFlavor::getDishId, dishId);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(lambdaQueryWrapper1);
            dishDto.setFlavors(dishFlavorList);
            return dishDto;
        }).collect(Collectors.toList());
        return R.success(res);
    }


    /**
     * @title update
     * @author SongJian
     * @param: ids
     * @updateTime 2022/12/7 20:04
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 批量停售菜品
     */
    @PostMapping("/status/0")
    public R<String> updateStop(@RequestParam List<Long> ids) {
        log.info("正在修改：{}", ids);
        dishService.updateStop(ids);
        return R.success("菜品成功停售");
    }

    /**
     * @title updateStart
     * @author SongJian
     * @param: ids
     * @updateTime 2022/12/7 20:24
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 批量起售菜品
     */
    @PostMapping("/status/1")
    public R<String> updateStart(@RequestParam List<Long> ids) {
        log.info("正在修改：{}", ids);
        dishService.updateStart(ids);
        return R.success("菜品成功起售");
    }


    /**
     * @title delete
     * @author SongJian
     * @param: ids
     * @updateTime 2022/12/7 20:30
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 批量删除菜品
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("正在删除：{}", ids);
        dishService.removeWithFlavor(ids);
        return R.success("删除菜品成功");
    }
}
 
