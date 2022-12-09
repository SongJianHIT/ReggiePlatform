/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.ShoppingCartServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.songjian.reggie.entity.ShoppingCart;
import tech.songjian.reggie.mapper.ShoppingCartMapper;
import tech.songjian.reggie.service.ShoppingCartService;

/**
 * ShoppingCartServiceImpl
 * @description
 * @author SongJian
 * @date 2022/12/9 10:27
 * @version
 */
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService{
}
 
