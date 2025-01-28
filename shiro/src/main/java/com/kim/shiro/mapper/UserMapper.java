package com.kim.shiro.mapper;

import com.kim.shiro.domain.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {

    User findByName(@Param("name") String name);

    User findById(@Param("id") Integer id);

}
