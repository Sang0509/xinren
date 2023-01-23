package com.example.springboot.controller;

import com.example.springboot.dao.UserDao;
import com.example.springboot.entity.User;
import com.example.springboot.entity.UserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    //@PostMapping("/login")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public User login(@RequestBody User user) {     //@RequestBody接受一个json参数
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new RuntimeException("参数错误");
        }
        User result = userDao.getByUser(user.getUsername(), user.getPassword());
        if (result == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        return result;
    }

    @GetMapping//localhost:9090/   //localhost:9090/任意字符串
    public List<User> findAll() {
        return userDao.findAll();
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable Integer id) {
        return userDao.getById(id);
    }

    @GetMapping("/uniqueQuery")
    public User uniqueQuery(String username, String password) {
        return userDao.getByUser(username,password);
    }

    @PostMapping
    public void save(@RequestBody User user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            throw new RuntimeException("参数错误");
        }
        userDao.insert(user);
    }

    @PutMapping
    public void update(@RequestBody User user) {
        if (user.getId() == null) {
            throw new RuntimeException("参数错误");
        }
        userDao.update(user);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Integer id) {
        if (id == null || id == 0) {
            throw new RuntimeException("参数错误");
        }
        return userDao.deletById(id) == 1;
    }

}