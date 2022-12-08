package tech.songjian.reggie.dto;


import lombok.Data;
import tech.songjian.reggie.entity.Setmeal;
import tech.songjian.reggie.entity.SetmealDish;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
