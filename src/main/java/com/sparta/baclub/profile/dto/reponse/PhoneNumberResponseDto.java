package com.sparta.baclub.profile.dto.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PhoneNumberResponseDto {
    private String username;
    private String phonenumber;
}
