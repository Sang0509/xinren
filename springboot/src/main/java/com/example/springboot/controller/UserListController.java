package com.example.springboot.controller;

import com.example.springboot.dao.ListDao;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class UserListController {
    @Autowired
    private ListDao listDao;

    @GetMapping("/list")
    public List<UserList> findAll() {
        return listDao.findAll();
    }
}