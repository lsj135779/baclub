package com.sparta.baclub.profile.entity;

import com.sparta.baclub.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "TB_PROFILE")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private String age;

    @Column(nullable = false)
    private String address;

    @Column
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Builder
    public Profile(final String username, final String nickname,
                   final String password, final String sex,
                   final String age, final String address,
                   final String phoneNumber) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateAddress(String address) {
        this.address = address;
    }

    public void updateAge(String age) {
        this.age = age;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}