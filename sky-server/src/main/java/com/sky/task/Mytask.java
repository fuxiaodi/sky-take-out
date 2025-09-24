package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

//@Component  //这是个demo,把@Component注销掉不让运行
@Slf4j
public class Mytask {

    @Scheduled(cron = "0/5 * * * * ?") //年可以不写
    public void executeTask(){
        log.info("定时任务开始执行：{}", new Date()); // Creates date object representing current date and time.

    }
}
