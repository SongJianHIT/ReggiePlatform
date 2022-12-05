/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.CategoryServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.songjian.reggie.common.CustomException;
import tech.songjian.reggie.entity.Category;
import tech.songjian.reggie.entity.Dish;
import tech.songjian.reggie.entity.Setmeal;
import tech.songjian.reggie.mapper.CategoryMapper;
import tech.songjian.reggie.service.CategoryService;
import tech.songjian.reggie.service.DishService;
import tech.songjian.reggie.service.SetmealService;

/**
 * CategoryServiceImpl
 * @description
 * @author SongJian
 * @date 2022/12/5 16:59
 * @version
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /**
     * @title remove
     * @author SongJian 
     * @param: id
     * @updateTime 2022/12/5 19:10 
     * @throws
     * @description 根据ID删除分类，删除之前需要判断关联情况
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper();
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper();
        // 1、查询当前分类是否关联了菜品，如果已经关联，则抛出业务异常
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count = dishService.count(dishLambdaQueryWrapper);
        if (count > 0) {
            // 说明分类已经关联了菜品，抛出异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        // 2、查询当前分类是否关联了套餐，如果已经关联，则抛出业务异常
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count1 > 0) {
            // 说明分类已经关联了套餐，抛出异常
            throw new CustomException("当前分类下关联了菜套餐，不能删除");
        }

        // 3、正常删除
        super.removeById(id);
    }
}
 
