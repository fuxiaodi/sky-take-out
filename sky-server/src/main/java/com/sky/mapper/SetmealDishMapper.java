package com.sky.mapper;

import java.util.List;

public interface SetmealDishMapper {

    /**
     * 根据菜品id查关联的套餐id
     * @param dishIds
     * @return
     */
     List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

}
