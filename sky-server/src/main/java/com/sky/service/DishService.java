package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品和对应的口味
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分类查询
     * @param dishPageQueryDTO
     * @return
     */
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO); //要加public,因为controller要调用

    /**
     * 菜品批量删除,连带删除关联的口味中的这个菜品
     * @param ids
     */
    void deleteBatch(List<Long> ids);


    /**
     * 根据id查询菜品
     * @param id
     */
    //public abstract
    DishVO getByIdWithFlavor(Long id);
}
