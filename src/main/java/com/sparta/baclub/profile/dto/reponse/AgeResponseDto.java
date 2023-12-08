package com.sparta.baclub.profile.dto.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AgeResponseDto {
    private String username;
    private int age;
}
