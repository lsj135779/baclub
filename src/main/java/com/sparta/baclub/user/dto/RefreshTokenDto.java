package com.sparta.baclub.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RefreshTokenDto {
    @NotEmpty
    String refreshToken;
}
