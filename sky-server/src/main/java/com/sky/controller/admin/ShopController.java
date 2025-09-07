package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("adminShopController")
@RequestMapping("/admin/shop")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate; //注入redis对象

    /**
     * 设置店铺营业状态
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("设置店铺营业状态")
    public Result setStatus(@PathVariable Integer status){
        log.info("设置店铺的营业状态为：{}", status == 1? "营业中": "打烊中");
        redisTemplate.opsForValue().set(KEY, String.valueOf(status));
        return Result.success(status);
    }

    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus(){
        redisTemplate.opsForValue().setIfAbsent(KEY, "1"); //如果redis中不存在SHOP_STATUS,默认设置为1
        String status = (String) redisTemplate.opsForValue().get(KEY);
        log.info("获取店铺的营业状态为：{}",  status.equals("1")? "营业中": "打烊中");
        return Result.success(Integer.valueOf(status));
    }


}
