package com.yy.controller;

import com.yy.domain.BlogUser;
import com.yy.mapper.UserMapper;
import com.yy.utils.ResponseData;
import com.yy.utils.SendSms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    public UserMapper userMapper;

    @RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
    public BlogUser selectById(@PathVariable("id") Integer id){
        return userMapper.selectById(id);
    }

    @RequestMapping(value = "/user/insert",method = RequestMethod.POST)
    public ResponseData insertUser(HttpServletRequest request){
        ResponseData responseData = new ResponseData();
        String name = request.getParameter("name");
        String password = (String)request.getParameter("password");
        String code = (String)request.getParameter("code");
        String tel = (String)request.getParameter("tel");
        String session_code =(String)request.getSession().getAttribute("code");
        if(code.equals(session_code)){
            responseData.setSuccess(true);
        }else {
            responseData.setSuccess(false);
            responseData.setMsg("验证码输入错误");
        }
        request.getSession().removeAttribute("code");
        return responseData;
    }

    @RequestMapping(value = "/user/sendVerifyYz")
    public void sendVerifyYz(String phone, HttpServletRequest request){
        String code = SendSms.send(phone);
        if(!"0".equals(code)){
            //将验证码放进session中
            request.getSession().setAttribute("code",code);
        }
    }
}
