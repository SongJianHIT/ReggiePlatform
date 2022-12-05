/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.service
 * @className tech.songjian.reggie.service.CategoryService
 */
package tech.songjian.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import tech.songjian.reggie.entity.Category;

/**
 * CategoryService
 * @description 分类Service接口
 * @author SongJian
 * @date 2022/12/5 16:58
 * @version
 */
public interface CategoryService extends IService<Category> {
    public void remove(Long id);
}
 
