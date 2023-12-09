package com.sparta.baclub.user.controller;


import com.sparta.baclub.CommonResponseDto;
import com.sparta.baclub.user.dto.*;
import com.sparta.baclub.user.entity.User;
import com.sparta.baclub.user.entity.UserRoleEnum;
import com.sparta.baclub.jwt.JwtUtil;
import com.sparta.baclub.user.service.RefreshTokenService;
import com.sparta.baclub.user.userDetails.UserDetailsImpl;
import com.sparta.baclub.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;



    @PostMapping("/signup")
    public ResponseEntity<CommonResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        try {
            userService.signup(requestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto("중복된 username 입니다.", HttpStatus.BAD_REQUEST.value()));
        }

        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(new CommonResponseDto("회원가입 성공", HttpStatus.CREATED.value()));
    }

    // 로그인 (access 토큰과 refresh 토큰 생성)
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            userService.login(loginRequestDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new CommonResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
        }
        User user = userService.findByUsername(loginRequestDto.getUsername());

        //Access Token 생성
        String accessToken = jwtUtil.createAcessToken(loginRequestDto.getUsername() ,UserRoleEnum.USER);
//        response.setHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createAcessToken(loginRequestDto.getUsername(), UserRoleEnum.USER));
        //Refresh Token 생성
        String refreshToken = jwtUtil.createRefreshToken(UserRoleEnum.USER);
//        response.setHeader(JwtUtil.REFRESH_AUTHORIZATION_HEADER, refreshToken);

        //RefreshToken을 DB에 저장한다. Redis를 사용해보자
        RefreshToken refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setValue(refreshToken);
        refreshTokenEntity.setUserId(user.getId());
        refreshTokenService.addRefreshToken(refreshTokenEntity);

        UserLoginResponseDto loginResponse = UserLoginResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .userId(user.getId())
                .nickname(user.getNickname())
                .build();

        return new ResponseEntity(loginResponse, HttpStatus.OK);
    }

    @DeleteMapping("/logout")
    public ResponseEntity logout(@RequestBody RefreshTokenDto refreshTokenDto) {
        refreshTokenService.deleteRefreshToken(refreshTokenDto.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK.value())
                .body(new CommonResponseDto("로그아웃 성공", HttpStatus.OK.value()));
    }

    @GetMapping("/user-info")
    @ResponseBody
    public UserInfoDto getUserInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        String username = userDetails.getUser().getUsername();
        UserRoleEnum role = userDetails.getUser().getRole();
        boolean isAdmin = (role == UserRoleEnum.ADMIN);

        return new UserInfoDto(username, isAdmin);
    }

//    @DeleteMapping("/signout")
//    public ResponseEntity<CommonResponseDto> signOut() {
//        userService.signOut();
//        return ResponseEntity.ok(new CommonResponseDto("탈퇴가 완료됐습니다.", HttpStatus.OK.value()));
//    }
}
