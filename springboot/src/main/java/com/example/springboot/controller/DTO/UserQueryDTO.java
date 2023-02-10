package com.example.springboot.controller.DTO;

import lombok.Data;

@Data
    public class UserQueryDTO {
    private Integer currentPage;
    private Integer pageNum;
    private Integer pageSize;
    private String name;
    private String email;
    private String telephone;
}
