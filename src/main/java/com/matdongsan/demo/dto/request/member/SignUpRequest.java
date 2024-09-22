package com.matdongsan.demo.dto.request.member;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank(message = "Please submit your name.")
    private String name;

    @NotBlank(message = "Please submit your id.")
    private String username;

    @NotBlank(message = "Please submit your pw.")
    private String password;
}
