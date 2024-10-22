package com.matdongsan.demo.mysql.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.io.IOException;
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

    @Column(name = "preferenceVector", columnDefinition = "TEXT")
    private String preferenceVector;

    @JsonIgnore
    @Builder.Default
    @OneToMany(mappedBy = "creator")
    private List<ChatRoom> chatRooms = new ArrayList<>();

    public List<Float> getPreferenceVectorList() {
        List<Float> preferenceVectorList = new ArrayList<>();
        String[] values = preferenceVector.split(",");

        for (String value : values) {
            preferenceVectorList.add(Float.parseFloat(value));
        }

        return preferenceVectorList;
    }

    public void setPreferenceVector(List<Float> preferenceVectorList) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < preferenceVectorList.size(); i++) {
            stringBuilder.append(preferenceVectorList.get(i));
            if (i < preferenceVectorList.size()-1) {
               stringBuilder.append(",");
            }
        }

        preferenceVector = stringBuilder.toString();
    }
}
