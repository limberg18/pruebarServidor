package com.taller.dto;

import lombok.Data;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
    private String rol; // ADMIN, VENDEDOR
}