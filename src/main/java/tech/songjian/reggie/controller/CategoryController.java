/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.controller
 * @className tech.songjian.reggie.controller.CategoryController
 */
package tech.songjian.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.songjian.reggie.common.BaseContext;
import tech.songjian.reggie.common.R;
import tech.songjian.reggie.entity.Category;
import tech.songjian.reggie.service.CategoryService;

import java.util.List;

/**
 * CategoryController
 * @description 分类Controller
 * @author SongJian
 * @date 2022/12/5 17:00
 * @version
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * @title save
     * @author SongJian
     * @param: category
     * @updateTime 2022/12/5 17:08
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 新增分类
     */
    @PostMapping
    public R<String> save(@RequestBody Category category) {
        log.info("category:{}", category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * @title page
     * @author SongJian
     * @param: page
     * @param: pageSize
     * @updateTime 2022/12/5 20:43
     * @return: tech.songjian.reggie.common.R<com.baomidou.mybatisplus.extension.plugins.pagination.Page>
     * @throws
     * @description 分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize) {
        // 分页构造器
        Page<Category> pageInfo = new Page<>(page, pageSize);
        // 条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        // 排序条件：根据sort进行排序
        queryWrapper.orderByAsc(Category::getSort);
        // 进行分页查询
        categoryService.page(pageInfo, queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * @title delete
     * @author SongJian
     * @param: id
     * @updateTime 2022/12/5 20:44
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 删除分类
     */
    @DeleteMapping
    public R<String> delete(Long id) {
        log.info("删除分类，id为 {}", id);

        // 根据 id 删除
        categoryService.remove(id);
        return R.success("删除成功");
    }

    /**
     * @title update
     * @author SongJian
     * @param: category
     * @updateTime 2022/12/5 20:45
     * @return: tech.songjian.reggie.common.R<java.lang.String>
     * @throws
     * @description 根据 id 修改分类信息
     */
    @PutMapping
    public R<String> update(@RequestBody Category category) {
        log.info("修改分类信息：{}", category);
        categoryService.updateById(category);
        return R.success("修改分类信息成功");
    }


    /**
     * @title list
     * @author SongJian
     * @param: category
     * @updateTime 2022/12/6 16:03
     * @return: tech.songjian.reggie.common.R<java.util.List<tech.songjian.reggie.entity.Category>>
     * @throws
     * @description 根据条件查询分类数据
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        // 条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();
        // 添加条件
        queryWrapper.eq(category.getType() != null, Category::getType, category.getType());
        // 排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
 
