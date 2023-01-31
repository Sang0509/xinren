package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.dao.ListDao;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserList;
import com.sun.javafx.collections.MappingChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class UserListController {

    @Autowired
    private ListDao listDao;

    @GetMapping("/list")
    public Result findAll() {
        return Result.success(listDao.findAll());
    }

    @GetMapping("/list/page") //    /list/page?currentPage=1&pageSize=10
    public Result findPage(@RequestParam Integer currentPage, @RequestParam Integer pageSize) {
        // currentPage = 1 pageNum = 0
        //currentPage = 2 pageNum = 3
        //currentPage = 3 pageNum = 6
        Integer pageNum = (currentPage - 1) * pageSize;
        List<UserList> lists = listDao.findPage(pageNum, pageSize);
        long total = listDao.count();

        Map<String, Object> map = new HashMap<>();
        map.put("data",lists);
        map.put("total",total);
        return Result.success(map);
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

