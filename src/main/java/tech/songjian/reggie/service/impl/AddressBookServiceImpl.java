/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service.impl
 * @className tech.songjian.reggie.service.impl.AddressBookServiceImpl
 */
package tech.songjian.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import tech.songjian.reggie.entity.AddressBook;
import tech.songjian.reggie.mapper.AddressBookMapper;
import tech.songjian.reggie.service.AddressBookService;

/**
 * AddressBookServiceImpl
 * @description
 * @author SongJian
 * @date 2022/12/8 16:23
 * @version
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements AddressBookService {
}
 
