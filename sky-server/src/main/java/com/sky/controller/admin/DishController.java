package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@ApiOperation("新增菜品")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    //当菜品出现增删改查和状态变化，需要清空user端的redis上的缓存
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);

        //清除缓存数据,哪一份缓存数据受影响，就只清除这一份数据
        String key = "dish_" + dishDTO.getCategoryId();
        clearCache(key);
        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("菜品分类查询")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("菜品分类查询: {}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }


    /**
     * 菜品批量删除
     * @param ids
     */
    @DeleteMapping
    @ApiOperation("菜品批量删除")
    public Result delete(@RequestParam List<Long> ids){ //@RequestParam把query链接里的ids = "1,2,3"自动转换成[1,2,3]
        log.info("菜品批量删除: {}", ids);
        dishService.deleteBatch(ids);

        //将所有的缓存数据清理掉
        clearCache("dish_*");
        return Result.success();
    }


    /**
     * 根据id查询菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")  //动态的id
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> getById(@PathVariable Long id){
        log.info("根据id查询菜品: {}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     * 修改菜品
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("修改菜品");
        dishService.updateWithFlavor(dishDTO);

        //将所有的缓存数据清理掉
        clearCache("dish_*"); //使用通配符
        return Result.success();
    }

    /**
     * 清理缓存数据
     * @param pattern
     */
    private void clearCache(String pattern){
        //将所有的缓存数据清理掉
        Set keys = redisTemplate.keys(pattern); //使用通配符
        redisTemplate.delete(keys);
    }

}
