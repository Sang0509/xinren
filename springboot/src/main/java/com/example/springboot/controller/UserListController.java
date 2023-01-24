package com.example.springboot.controller;

import com.example.springboot.dao.ListDao;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping
    public void save(@RequestBody UserList userList) {
        String name = listDao.findName(userList.getName());
        if (name.equals(userList.getName())){
            listDao.update(userList);
        }else{
            listDao.insert(userList);
        }
    }
}

