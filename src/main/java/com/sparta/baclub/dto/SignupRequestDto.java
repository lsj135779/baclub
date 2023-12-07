package com.sparta.baclub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\d`~!@#$%^&*()-_=+]{8,15}$")
    private String password;
    @NotBlank
    private String nickname;
    @NotBlank
    private String phoneNumber;
    private String sex;
    int age;
    private String address;
    boolean admin = false;
    private String adminToken = "";
}