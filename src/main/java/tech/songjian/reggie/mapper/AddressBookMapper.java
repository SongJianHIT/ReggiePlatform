/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.mapper
 * @className tech.songjian.reggie.mapper.AddressBookMapper
 */
package tech.songjian.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.songjian.reggie.entity.AddressBook;

/**
 * AddressBookMapper
 * @description
 * @author SongJian
 * @date 2022/12/8 16:21
 * @version
 */
@Mapper
public interface AddressBookMapper extends BaseMapper<AddressBook> {
}
 
