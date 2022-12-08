/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.SetmealServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.songjian.reggie.common.CustomException;
import tech.songjian.reggie.dto.SetmealDto;
import tech.songjian.reggie.entity.Setmeal;
import tech.songjian.reggie.entity.SetmealDish;
import tech.songjian.reggie.mapper.SetmealMapper;
import tech.songjian.reggie.service.SetmealDishService;
import tech.songjian.reggie.service.SetmealService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * SetmealServiceImpl
 * @description
 * @author SongJian
 * @date 2022/12/5 19:20
 * @version
 */
@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private SetmealDishService setmealDishService;

    /**
     * @param setmealDto
     * @throws
     * @title saveWithDish
     * @author SongJian
     * @updateTime 2022/12/7 15:19
     * @description 新增套餐，同时保存套餐和菜品组合
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveWithDish(SetmealDto setmealDto) {
        // 保存套餐：操作setmeal，执行insert操作
        this.save(setmealDto);
        // 保存套餐与菜品关联关系，操作setmeal_dish，执行insert操作
        Long setmealId = setmealDto.getId();
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        List<SetmealDish> list= setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealId);
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(list);
    }

    /**
     * @param ids
     * @throws
     * @title removeWithDish
     * @author SongJian
     * @param: ids
     * @updateTime 2022/12/7 17:04
     * @description 删除套餐，同时删除套餐与菜品的关联数据
     */
    @Transactional(rollbackFor = Exception.class)
    public void removeWithDish(List<Long> ids) {
        // 1、只删除停售套餐，先查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 多个关键字，用in
        // select count(*) from setmeal where id in (1,2,3) and status = 1;
        lambdaQueryWrapper.in(Setmeal::getId, ids).eq(Setmeal::getStatus, 1);
        int count = this.count(lambdaQueryWrapper);

        // 2、如果不能删除，抛出业务异常
        if (count > 0) {
            throw new CustomException("套餐正在售卖中，不能删除");
        }
        // 3、如果可以删除，先删除套餐表中的数据 --setmeal
        this.removeByIds(ids);

        // 4、删除关系表中的数据 -- setmeal_dish
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper1 = new LambdaQueryWrapper();
        // delete from setmeal_dish where setmeal_id in (1,2,3);
        lambdaQueryWrapper1.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper1);
    }
}
 
