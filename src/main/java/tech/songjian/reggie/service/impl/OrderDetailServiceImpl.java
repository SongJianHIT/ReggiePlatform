/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.OrderDetailServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.songjian.reggie.entity.OrderDetail;
import tech.songjian.reggie.mapper.OrderDetailMapper;
import tech.songjian.reggie.service.OrderDetailService;

/**
 * OrderDetailServiceImpl
 * @description
 * @author SongJian
 * @date 2022/12/9 16:42
 * @version
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
 
