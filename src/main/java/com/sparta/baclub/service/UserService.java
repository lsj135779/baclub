package com.sparta.baclub.service;

import com.sparta.baclub.dto.CommonResponseDto;
import com.sparta.baclub.dto.LoginRequestDto;
import com.sparta.baclub.dto.SignupRequestDto;
import com.sparta.baclub.entity.User;
import com.sparta.baclub.profile.dto.reponse.*;
import com.sparta.baclub.profile.dto.request.AddressRequestDto;
import com.sparta.baclub.profile.dto.request.AgeRequestDto;
import com.sparta.baclub.profile.dto.request.NicknameRequestDto;
import com.sparta.baclub.profile.dto.request.PhoneNumberRequestDto;
import com.sparta.baclub.profile.entity.Profile;
import com.sparta.baclub.repository.UserRepository;
import com.sparta.baclub.entity.UserRoleEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
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

    @Transactional
    public NicknameResponseDto updateNickname(final User user, final NicknameRequestDto requestDto) throws ChangeSetPersister.NotFoundException {
        Optional<User> findUser = userRepository.findByNickname(requestDto.getNickname());
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("사용자의 아이디가 아닙니다.");
        }

        User loginUser = userRepository.findById(user.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        loginUser.updateNickname(requestDto.getNickname());

        return NicknameResponseDto.builder()
                .username(loginUser.getUsername())
                .nickname(loginUser.getNickname())
                .build();
    }

    @Transactional
    public AddressResponseDto updateAddress(final User user, final AddressRequestDto requestDto) throws ChangeSetPersister.NotFoundException {
        Optional<User> findUser = userRepository.findByAddress(requestDto.getAddress());
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("사용자의 아이디가 아닙니다.");
        }

        User loginUser = userRepository.findById(user.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        loginUser.updateAddress(requestDto.getAddress());

        return AddressResponseDto.builder()
                .username(loginUser.getUsername())
                .address(loginUser.getAddress())
                .build();
    }

    @Transactional
    public AgeResponseDto updateAge(final User user, final AgeRequestDto requestDto) throws ChangeSetPersister.NotFoundException {
        Optional<User> findUser = userRepository.findByAge(requestDto.getAge());
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("사용자의 아이디가 아닙니다.");
        }

        User loginUser = userRepository.findById(user.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        loginUser.updateAge(requestDto.getAge());

        return AgeResponseDto.builder()
                .username(loginUser.getUsername())
                .age(loginUser.getAge())
                .build();
    }

    public PhoneNumberResponseDto updatePhoneNumber(final User user, final PhoneNumberRequestDto requestDto) throws ChangeSetPersister.NotFoundException {
        Optional<User> findUser = userRepository.findByPhoneNumber(requestDto.getPhoneNumber());
        if (findUser.isPresent()) {
            throw new IllegalArgumentException("사용자의 아이디가 아닙니다.");
        }
        User loginUSer = userRepository.findById(user.getId()).orElseThrow(ChangeSetPersister.NotFoundException::new);

        loginUSer.updatePhoneNumber(requestDto.getPhoneNumber());

        return PhoneNumberResponseDto.builder()
                .username(loginUSer.getUsername())
                .phonenumber(loginUSer.getPhoneNumber())
                .build();
    }

    public Profile getUserData(final User user) {
        return Profile.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .age(String.valueOf(user.getAge()))
                .sex(user.getSex())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}