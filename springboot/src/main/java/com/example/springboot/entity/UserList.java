package com.example.springboot.entity;

import lombok.Data;

@Data
public class UserList<L> {
    private String name;
    private String sex;
    private String department;
    private String birthday;
    private String email;
    private String telephone;
    private String skill;
    private String educational;
    private String address;
    private int del_flag;
}