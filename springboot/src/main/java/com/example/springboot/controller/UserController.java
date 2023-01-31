package com.example.springboot.controller;

import com.example.springboot.common.Result;
import com.example.springboot.dao.UserDao;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    //@PostMapping("/login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User user) {     //@RequestBody接受一个json参数
        if (user.getUsername() == null || user.getPassword() == null) {
            return Result.error("参数错误");
        }
        User result = userDao.getByUser(user.getUsername(), user.getPassword());
        if (result == null) {
            return Result.error("メールアドレスまたはパスワードが間違っています");
        }
        return Result.success(result);
    }

    @GetMapping//localhost:9090/   //localhost:9090/任意字符串
    public Result findAll() {
        return Result.success(userDao.findAll());
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id) {
        return Result.success(userDao.getById(id));
    }

    @GetMapping("/uniqueQuery")
    public Result uniqueQuery(String username, String password) {
        return Result.success(userDao.getByUser(username,password));
    }

    @PostMapping
    public Result save(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return Result.error("参数错误");
        }
        userDao.insert(user);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody User user) {
        if (user.getId() == null) {
            return Result.error("参数错误");
        }
        userDao.update(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        if (id == null || id == 0) {
            return Result.error("参数错误");
        }
        return Result.success(userDao.deletById(id) == 1);
    }

}