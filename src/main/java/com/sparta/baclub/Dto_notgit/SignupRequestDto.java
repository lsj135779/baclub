package com.sparta.baclub.Dto_notgit;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;

    @Pattern(regexp = "^[a-zA-Z\\d`~!@#$%^&*()-_=+]{8,15}$")
    private String password;

    private String nickname;
    private String sex;
    private int age;
    private String address;
    private String phoneNumber;

    private boolean admin = false;
    private String adminToken = "";
}