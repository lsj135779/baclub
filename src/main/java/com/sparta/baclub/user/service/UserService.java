package com.sparta.baclub.service;

import com.sparta.baclub.dto.LoginRequestDto;
import com.sparta.baclub.dto.SignupRequestDto;
import com.sparta.baclub.entity.User;
import com.sparta.baclub.repository.UserRepository;
import com.sparta.baclub.entity.UserRoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    // ADMIN_TOKEN-----------------------------------------------------------
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
    //-----------------------------------------------------------------------

    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword()); //passwordEncoder.encode()
        String nickname = requestDto.getNickname();
        String phoneNumber = requestDto.getPhoneNumber();

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 닉네임 중복 확인
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 존재합니다.");
        }

        // 전화번호 중복 확인
        Optional<User> checkPhoneNumber = userRepository.findByPhoneNumber(phoneNumber);
        if (checkPhoneNumber.isPresent()) {
            throw new IllegalArgumentException("중복된 휴대폰 번호가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, nickname, requestDto.getAge(), requestDto.getSex(), requestDto.getAddress(), phoneNumber, role);
        userRepository.save(user);
    }

//    public void login(LoginRequestDto requestDto, HttpServletResponse res) {
//        String username = requestDto.getUsername();
//        String password = requestDto.getPassword();
//
//        // 사용자 확인
//        User user = userRepository.findByUsername(username).orElseThrow(() ->
//            new IllegalArgumentException("등록되지 않은 계정입니다.")
//        );
//
//        // 비밀번호 확인
//        if(!passwordEncoder.matches(password, user.getPassword())) {
//            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
//        }
//
//        //JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
//        //*** user.getNickname으로 해야할까?
//        String token = jwtUtil.createToken(user.getUsername(), user.getRole());
//        jwtUtil.addJwtToCookie(token, res);
//    }


    public void login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록된 유저가 없습니다."));

        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 틀렸습니다.");
        }
    }
}