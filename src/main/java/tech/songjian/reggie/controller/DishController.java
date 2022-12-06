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


}
 
