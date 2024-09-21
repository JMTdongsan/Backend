package com.matdongsan.demo.mysql.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memberId;

    @Column(name = "name", unique = true)
    private String name;

    @NonNull
    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @NonNull
    @Column(name = "role")
    private String role;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "creator")
    private List<ChatRoom> chatRooms = new ArrayList<>();
}
