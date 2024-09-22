package com.matdongsan.demo.controller;

import com.matdongsan.demo.dto.request.member.GetAllChatRoomsRequest;
import com.matdongsan.demo.dto.response.member.GetAllChatRoomsResponse;
import com.matdongsan.demo.service.memberservice.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/getAllChatRoomsResponse")
    public ResponseEntity<GetAllChatRoomsResponse> getAllChatRooms(@Valid @RequestBody GetAllChatRoomsRequest request) {
        return ResponseEntity.ok(memberService.getAllChatRooms(request));
    }
}
