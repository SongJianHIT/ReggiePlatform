/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.mapper
 * @className tech.songjian.reggie.mapper.CategoryMapper
 */
package tech.songjian.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import tech.songjian.reggie.entity.Category;

/**
 * CategoryMapper
 * @description 分类Mapper
 * @author SongJian
 * @date 2022/12/5 16:56
 * @version
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}