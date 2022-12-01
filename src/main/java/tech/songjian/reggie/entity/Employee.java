/**
 * @projectName reggie_take_out
 * @package tech.songjian.reggie.entity
 * @className tech.songjian.reggie.entity.Employee
 */
package tech.songjian.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Employee
 * @description 员工实体类
 * @author SongJian
 * @date 2022/12/1 20:09
 * @version
 */
@Data
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long id;

    /**
     * 登入用户名
     */
    private String username;

    /**
     * 名字
     */
    private String name;

    /**
     * 登入密码
     */
    private String password;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 性别
     */
    private String sex;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 用户状态：
     *      - 1：正常使用状态
     *      - 0：账号被锁定
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
 
