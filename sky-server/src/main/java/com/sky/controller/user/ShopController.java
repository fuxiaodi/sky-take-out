package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Slf4j
public class ShopController {

    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate; //注入redis对象


    @GetMapping("/status")
    @ApiOperation("获取店铺的营业状态")
    public Result<Integer> getStatus(){
       // redisTemplate.opsForValue().setIfAbsent(KEY, "1"); //如果redis中不存在SHOP_STATUS,默认设置为1
        Integer status = (Integer) Optional.ofNullable(redisTemplate.opsForValue().get(KEY)).orElse(1);
        log.info("获取店铺的营业状态为：{}",  status.equals("1")? "营业中": "打烊中");
        return Result.success(status);
    }


}
