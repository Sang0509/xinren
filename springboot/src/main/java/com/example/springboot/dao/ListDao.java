package com.example.springboot.dao;

import com.example.springboot.controller.DTO.UserQueryDTO;
import org.apache.ibatis.annotations.*;
import com.example.springboot.entity.UserList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ListDao {

    List<UserList> findAll();

    @Select("select ifnull((select name from `list` where `name`= #{name }), 0) as `name`;")
    String findName(String name);

    int insert(UserList userList);

    //    @Update("update `list` set sex=#{sex},)
    int update(UserList userList);

    int deleteByName(String name);

    List<UserList> findPage(UserQueryDTO userQueryDTO);

    long count(UserQueryDTO userQueryDTO);
}