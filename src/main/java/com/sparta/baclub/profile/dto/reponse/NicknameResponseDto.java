package com.sparta.baclub.profile.dto.reponse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NicknameResponseDto {
    private String username;
    private String nickname;
}
