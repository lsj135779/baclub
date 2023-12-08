package com.sparta.baclub.user.entity;

import com.sparta.baclub.Timestamped;
import com.sparta.baclub.board.entity.BoardEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@Table(name = "users")
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private int age;

    @Column
    private String sex;

    @Column
    private String address;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<BoardEntity> BoardList;

    public User(String username, String password, String nickname, int age, String sex, String address, String phoneNumber, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age =  age;
        this.sex = sex;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

}

//