package com.sky.service;


import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

import javax.xml.transform.Result;

public interface UserService {

    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    User wxLogin(UserLoginDTO userLoginDTO);
}
