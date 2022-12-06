package tech.songjian.reggie.dto;

import lombok.Data;
import tech.songjian.reggie.entity.Dish;
import tech.songjian.reggie.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
