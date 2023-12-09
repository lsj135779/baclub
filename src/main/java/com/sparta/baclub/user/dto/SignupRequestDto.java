package com.sparta.baclub.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$",
             message = "아이디는 영문+숫자를 포함한 4~10자리여야 합니다.")
    private String username;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\d`~!@#$%^&*()-_=+]{8,15}$",
             message = "비밀번호는 영문+숫자+특수문자를 포함한 8~15자리여야 합니다.")
    private String password;
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9가-힣\\\\s]{2,15}$",
             message = "닉네임은 영문자, 한글, 공백 포함 2~15글자까지 가능합니다.")
    private String nickname;
    @NotBlank
    private String phoneNumber;
    private String sex;
    int age;
    private String address;
    boolean admin = false;
    private String adminToken = "";
}