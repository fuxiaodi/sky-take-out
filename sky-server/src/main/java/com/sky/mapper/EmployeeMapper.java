package com.sky.mapper;

import com.sky.entity.Employee;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper //业务层跟数据层交流，使用@Select, @Insert注解写sql语句，执行sql语句
public interface EmployeeMapper {

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    /**
     * 插入员工数据
     * @param employee
     */
    @Insert("INSERT INTO employee (name, username, password, phone, sex, id_number, create_time, update_time, create_user, status) " +
            "VALUES (#{name}, #{username}, #{password}, #{phone}, #{sex}, #{idNumber}, #{createTime}, #{updateTime}, #{createUser}, #{status})")
    void insert(Employee employee);  //跟数据库进行交流，使用entity


}
