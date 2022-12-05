/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.SetmealServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.songjian.reggie.entity.Setmeal;
import tech.songjian.reggie.mapper.SetmealMapper;
import tech.songjian.reggie.service.SetmealService;

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
}
 
