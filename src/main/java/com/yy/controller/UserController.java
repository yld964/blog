package com.yy.controller;

import com.yy.domain.BlogUser;
import com.yy.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    public UserMapper userMapper;

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public BlogUser selectById(@PathVariable("id") Integer id){
        return userMapper.selectById(id);
    }
}
