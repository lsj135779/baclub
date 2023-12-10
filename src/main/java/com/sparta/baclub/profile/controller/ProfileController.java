package com.sparta.baclub.profile.controller;



import com.sparta.baclub.profile.dto.reponse.AddressResponseDto;
import com.sparta.baclub.profile.dto.reponse.AgeResponseDto;
import com.sparta.baclub.profile.dto.reponse.NicknameResponseDto;
import com.sparta.baclub.profile.dto.reponse.PhoneNumberResponseDto;
import com.sparta.baclub.profile.dto.request.AddressRequestDto;
import com.sparta.baclub.profile.dto.request.AgeRequestDto;
import com.sparta.baclub.profile.dto.request.NicknameRequestDto;
import com.sparta.baclub.profile.dto.request.PhoneNumberRequestDto;
import com.sparta.baclub.profile.entity.Profile;
import com.sparta.baclub.user.entity.User;
import com.sparta.baclub.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {

    private UserService userService;
    private Profile profile;

    @GetMapping
    public ResponseEntity<Profile> myProfile(@AuthenticationPrincipal User user) {
        Profile profile1 = userService.getUserData(user);
        return ResponseEntity.ok(profile1);
    }

    @PostMapping("/nickname")
    public ResponseEntity<NicknameResponseDto> updateNickname(@RequestBody NicknameRequestDto nicknameRequestDto,
                                                              @AuthenticationPrincipal User user) throws ChangeSetPersister.NotFoundException {
        NicknameResponseDto responseDto = userService.updateNickname(user, nicknameRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/address")
    public ResponseEntity<AddressResponseDto> updateAddress(@RequestBody AddressRequestDto addressRequestDto,
                                                            @AuthenticationPrincipal User user) throws ChangeSetPersister.NotFoundException {
        AddressResponseDto responseDto = userService.updateAddress(user, addressRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/age")
    public ResponseEntity<AgeResponseDto> updateAge(@RequestBody AgeRequestDto ageRequestDto,
                                                    @AuthenticationPrincipal User user) throws ChangeSetPersister.NotFoundException {
        AgeResponseDto responseDto = userService.updateAge(user, ageRequestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PostMapping("/phonenumber")
    public ResponseEntity<PhoneNumberResponseDto> updatePhonenumber(@RequestBody PhoneNumberRequestDto phoneNumberRequestDto,
                                                                    @AuthenticationPrincipal User user) throws ChangeSetPersister.NotFoundException {
        PhoneNumberResponseDto responseDto = userService.updatePhoneNumber(user, phoneNumberRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}
