package com.example.springboot.dao;

import org.apache.ibatis.annotations.Mapper;
import com.example.springboot.entity.UserList;

import java.util.List;


@Mapper
public interface ListDao {

    List<UserList> findAll();
}
