package com.matdongsan.demo.controller;

import com.matdongsan.demo.dto.request.member.SignUpRequest;
import com.matdongsan.demo.dto.response.member.SignUpResponse;
import com.matdongsan.demo.service.memberservice.SignUpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SignUpController {

    private final SignUpService signUpService;

    @PostMapping("/signUp")
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest request) {

        return ResponseEntity.ok(signUpService.signUp(request));
    }
}
