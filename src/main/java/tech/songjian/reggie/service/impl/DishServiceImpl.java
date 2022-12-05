/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.DishServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.songjian.reggie.entity.Dish;
import tech.songjian.reggie.mapper.DishMapper;
import tech.songjian.reggie.service.DishService;

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


}
 
