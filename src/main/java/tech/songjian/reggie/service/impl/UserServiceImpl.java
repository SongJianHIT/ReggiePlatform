/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.UserServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.songjian.reggie.entity.User;
import tech.songjian.reggie.mapper.UserMapper;
import tech.songjian.reggie.service.UserService;

/**
 * UserServiceImpl
 * @description
 * @author SongJian
 * @date 2022/12/7 21:35
 * @version
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
 
