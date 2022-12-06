/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.DishFlavorServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.songjian.reggie.entity.DishFlavor;
import tech.songjian.reggie.mapper.DishFlavorMapper;
import tech.songjian.reggie.service.DishFlavorService;

/**
 * DishFlavorServiceImpl
 * @description
 * @author SongJian
 * @date 2022/12/6 15:52
 * @version
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
 
