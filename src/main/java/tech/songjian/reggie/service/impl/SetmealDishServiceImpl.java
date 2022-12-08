/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.SetmealDishServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.songjian.reggie.dto.SetmealDto;
import tech.songjian.reggie.entity.SetmealDish;
import tech.songjian.reggie.mapper.SetmealDishMapper;
import tech.songjian.reggie.service.SetmealDishService;

/**
 * SetmealDishServiceImpl
 * @description
 * @author SongJian
 * @date 2022/12/7 14:18
 * @version
 */
@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
 
