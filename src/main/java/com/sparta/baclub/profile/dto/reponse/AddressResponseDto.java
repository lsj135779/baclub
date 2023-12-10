package com.sparta.baclub.profile.dto.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AddressResponseDto {
    private String username;
    private String address;
}
