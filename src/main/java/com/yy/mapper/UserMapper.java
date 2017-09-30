package com.yy.mapper;

import com.yy.domain.BlogUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface UserMapper {
    @Select("select * from blog_user where id=#{id}")
    BlogUser selectById(Integer id);
}
