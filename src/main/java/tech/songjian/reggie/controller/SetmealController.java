/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.controller
 * @className tech.songjian.reggie.controller.SetmealController
 */
package tech.songjian.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;
import tech.songjian.reggie.common.R;
import tech.songjian.reggie.dto.DishDto;
import tech.songjian.reggie.dto.SetmealDto;
import tech.songjian.reggie.entity.Category;
import tech.songjian.reggie.entity.Dish;
import tech.songjian.reggie.entity.Setmeal;
import tech.songjian.reggie.service.CategoryService;
import tech.songjian.reggie.service.SetmealDishService;
import tech.songjian.reggie.service.SetmealService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SetmealController
 * @description 套餐controller
 * @author SongJian
 * @date 2022/12/7 14:20
 * @version
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private CategoryService categoryService;


    /**
     * @title save
     * @author SongJian
     * @param: setmealDto
     * @updateTime 2022/12/7 15:08
     * @return: R<String>
     * @throws
     * @description 新增套餐
     */
    @PostMapping
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R<String> save(@RequestBody SetmealDto setmealDto) {
        setmealService.saveWithDish(setmealDto);
        return R.success("新增套餐成功");
    }


    /**
     * @title page
     * @author SongJian
     * @param: page
     * @param: pageSize
     * @updateTime 2022/12/7 16:25
     * @return: tech.songjian.reggie.common.R<java.util.List<tech.songjian.reggie.entity.Dish>>
     * @throws
     * @description 分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDto> pageDtoInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(name != null, Setmeal::getName, name);
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, lambdaQueryWrapper);

        // 对象拷贝
        BeanUtils.copyProperties(pageInfo, pageDtoInfo, "records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> list = records.stream().map((item)->{
            SetmealDto setmealDto = new SetmealDto();
            BeanUtils.copyProperties(item, setmealDto);
            // 获取id
            Long categoryId = item.getCategoryId();
            // 根据分类id获取分类对象
            Category category = categoryService.getById(categoryId);
            if (category != null) {
                String categoryName = category.getName();
                setmealDto.setCategoryName(categoryName);
            }
            return setmealDto;
        }).collect(Collectors.toList());

        pageDtoInfo.setRecords(list);
        return R.success(pageDtoInfo);
    }

    /**
     * @title delete
     * @author SongJian 
     * @updateTime 2022/12/7 16:56 
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 删除一个、或多个套餐
     */
    @DeleteMapping
    @CacheEvict(value = "setmealCache", allEntries = true)
    public R<String> delete(@RequestParam List<Long> ids) {
        log.info("正在删除菜品:{}", ids);
        setmealService.removeWithDish(ids);
        return R.success("删除菜品成功");
    }


    /**
     * @title list
     * @author SongJian
     * @updateTime 2022/12/8 17:11
     * @return: tech.songjian.reggie.common.R<java.util.List<tech.songjian.reggie.dto.DishDto>>
     * @throws
     * @description 根据条件查询套餐数据
     */
    @GetMapping("/list")
    @Cacheable(value = "setmealCache", key = "#setmeal.categoryId + '_' + #setmeal.status")
    public R<List<Setmeal>> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        lambdaQueryWrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus());
        lambdaQueryWrapper.orderByDesc(Setmeal::getUpdateTime);

        List<Setmeal> list = setmealService.list(lambdaQueryWrapper);

        return R.success(list);
    }
}
 
