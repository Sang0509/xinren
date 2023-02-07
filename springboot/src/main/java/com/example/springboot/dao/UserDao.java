package com.example.springboot.dao;

import com.example.springboot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    List<User> findAll();

    User getById(Integer id);

    User getByUser(@Param("email") String email, @Param("password") String password);

    int insert(User user);

    int update(User user);

    int deletById(Integer id);
}
