package com.example.springboot.controller;

import com.example.springboot.common.Result;
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
    public Result findAll() {
        return Result.success(listDao.findAll());
    }

    @PostMapping
    public Result save(@RequestBody UserList userList) {
        String name = listDao.findName(userList.getName());
        if (name.equals(userList.getName())) {
            listDao.update(userList);
        } else {
            listDao.insert(userList);
        }
        return Result.success();
    }

    @DeleteMapping("/{name}")
    public Result delete(@PathVariable String name) {
        if (name == null || name.equals("")) {
            return Result.error("参数错误");
        }
        return Result.success(listDao.deleteByName(name) == 1);
    }
}

