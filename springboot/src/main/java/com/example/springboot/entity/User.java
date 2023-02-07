package com.example.springboot.entity;

import lombok.Data;

@Data
public class User {
    private Integer id;
    //ユーザー名
    private String username;
    //パスワード
    private String password;
    private String email;
}
