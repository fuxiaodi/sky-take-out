package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetmealDishMapper {

    /**
     * 根据菜品id查关联的套餐id
     * @param dishIds
     * @return
     */
     List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * 插入跟套餐管理的菜品数据
     * @param setmealDishes
     */
    void insertBatch(List<SetmealDish> setmealDishes);
}
