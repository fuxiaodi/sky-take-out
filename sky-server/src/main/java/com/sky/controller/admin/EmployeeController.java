package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController  //表示这是一个 REST 接口 控制器，常配合 @RequestMapping / @GetMapping / @PostMapping 等定义路由。
// 等价于 @Controller + @ResponseBody：方法返回值直接写入 HTTP 响应体，不走页面视图解析。
@RequestMapping("/admin/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired  //Please inject (provide) an instance of this bean here, automatically.
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "员工登录")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {

        try {
            log.info("员工登录：{}", employeeLoginDTO);

            Employee employee = employeeService.login(employeeLoginDTO);

            //登录成功后，生成jwt令牌
            Map<String, Object> claims = new HashMap<>();
            claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
            String token = JwtUtil.createJWT(
                    jwtProperties.getAdminSecretKey(),
                    jwtProperties.getAdminTtl(),
                    claims);

            EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                    .id(employee.getId())
                    .userName(employee.getUsername())
                    .name(employee.getName())
                    .token(token)
                    .build();
            log.info("登录成功");
            return Result.success(employeeLoginVO);
        }catch (Exception e) {
            log.error("员工登录失败：{}", e.getMessage(), e);
            return Result.error("登录失败，请联系管理员");
        }
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 新增员工
     * @param employeeDTO
     * @return
     */
    @PostMapping //在类的前面已经加了@RequestMapping("/admin/employee")，这就不用加
    @ApiOperation("新增员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO){ //如果前端提交的是json格式
        log.info("新增员工: {}", employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();
    }

}
