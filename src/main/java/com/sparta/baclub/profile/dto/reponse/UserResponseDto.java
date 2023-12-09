package com.sparta.baclub.profile.dto.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String nickname;
    private final String username;
    private final String sex;
    private final String age;
    private final String address;
    private final String phoneNumber;

    @Builder
    public UserResponseDto(final String nickname, final String username,
                           final String sex, final String age,
                           final String address, final String phoneNumber) {
        this.nickname = nickname;
        this.username = username;
        this.sex = sex;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}
