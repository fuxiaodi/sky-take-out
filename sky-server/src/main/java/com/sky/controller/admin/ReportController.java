package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.TurnoverReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

/**
 * 数据统计相关接口
 */
@RestController  //@RestController is an annotation used to create REST API controllers.
@RequestMapping("/admin/report/")
@Api(tags = "数据统计相关接口")
@Slf4j
public class ReportController {

    @Autowired
    private ReportService reportService;


    /**
     * 营业额统计
     * @param begin 开始日期
     * @param end 结束日期
     * @return
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end){
        log.info("营业额数据统计,{},{}", begin, end);
        try {
            return Result.success(reportService.getTurnoverstatistics(begin, end));
        }catch (Exception e){
            log.error("Turnover statistics error", e);
            return Result.error("Turnover statistics error");
        }
    }
}
