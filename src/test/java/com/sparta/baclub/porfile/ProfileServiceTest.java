package com.sparta.baclub.porfile;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import com.sparta.baclub.entity.User;
import com.sparta.baclub.entity.UserRoleEnum;
import com.sparta.baclub.profile.dto.reponse.*;
import com.sparta.baclub.profile.dto.request.AddressRequestDto;
import com.sparta.baclub.profile.dto.request.AgeRequestDto;
import com.sparta.baclub.profile.dto.request.NicknameRequestDto;
import com.sparta.baclub.profile.dto.request.PhoneNumberRequestDto;
import com.sparta.baclub.profile.entity.Profile;
import com.sparta.baclub.repository.UserRepository;
import com.sparta.baclub.service.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.crypto.password.PasswordEncoder;

@Transactional
@SpringBootTest
public class ProfileServiceTest {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    // 가상의 테스트 유저 생성
    private User insertUser() {
        User user = User.builder()
                .username("testusername")
                .password("testpassword")
                .nickname("testnickname")
                .age(20)
                .sex("testsex")
                .address("testaddress")
                .phoneNumber("testphonenumber")
                .role(UserRoleEnum.USER)
                .build();
        return userRepository.save(user);
    }

    @Test
    @DisplayName("프로필 조회")
    public void myProfileTest() {
        // given
        User user = insertUser();

        // when
        Profile responseDto = userService.getUserData(user);

        // then
        assertThat(responseDto).isNotNull();
        }

    @Test
    @DisplayName("닉네임 수정")
    public void updateNicknameTest() throws ChangeSetPersister.NotFoundException {
        //given
        User user = insertUser();
        String changeNickname = "change nickname";
        NicknameRequestDto requestDto = NicknameRequestDto.builder()
                .nickname(changeNickname)
                .build();

        // when
        NicknameResponseDto responseDto = userService.updateNickname(user, requestDto);

        // then
        User changedUser = userRepository.findById(user.getId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getNickname()).isEqualTo(changeNickname);
        assertThat(changedUser.getNickname()).isEqualTo(changeNickname);
    }

    @Test
    @DisplayName("핸드폰 번호 수정")
    public void updatePhoneNumberTest() throws ChangeSetPersister.NotFoundException {
        // given
        User user = insertUser();
        String changePhoneNumber = "change PhoneNumber";
        PhoneNumberRequestDto requestDto = PhoneNumberRequestDto.builder()
                .phoneNumber(changePhoneNumber)
                .build();

        // when
        PhoneNumberResponseDto responseDto = userService.updatePhoneNumber(user, requestDto);

        // then
        User changeUser = userRepository.findById(user.getId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getPhonenumber()).isEqualTo(changePhoneNumber);
        assertThat(changeUser.getPhoneNumber()).isEqualTo(changePhoneNumber);
    }

    @Test
    @DisplayName("주소 수정")
    public void updateAddress() throws ChangeSetPersister.NotFoundException {
        // given
        User user = insertUser();
        String changeAddress = "change Address";
        AddressRequestDto requestDto = AddressRequestDto.builder()
                .address(changeAddress)
                .build();

        // when
        AddressResponseDto responseDto = userService.updateAddress(user, requestDto);

        // then
        User changeUser = userRepository.findById(user.getId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getAddress()).isEqualTo(changeAddress);
        assertThat(changeUser.getAddress()).isEqualTo(changeAddress);
    }

    @Test
    @DisplayName("나이 수정")
    public void updateAge() throws ChangeSetPersister.NotFoundException {
        // given
        User user = insertUser();
        int changeAge = 21;
        AgeRequestDto requestDto = AgeRequestDto.builder()
                .age(changeAge)
                .build();

        // when
        AgeResponseDto responseDto = userService.updateAge(user, requestDto);

        // then
        User changeUser = userRepository.findById(user.getId())
                .orElseThrow(ChangeSetPersister.NotFoundException::new);

        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getAge()).isEqualTo(changeAge);
        assertThat(changeUser.getAge()).isEqualTo(changeAge);
    }

}
