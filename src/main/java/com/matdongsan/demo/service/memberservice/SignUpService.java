package com.matdongsan.demo.service.memberservice;

import com.matdongsan.demo.mysql.domain.Member;
import com.matdongsan.demo.dto.request.member.SignUpRequest;
import com.matdongsan.demo.dto.response.member.SignUpResponse;
import com.matdongsan.demo.mysql.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignUpService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {

        Boolean isExistedByName = memberRepository.existsByName(request.getName());
        Boolean isExistedByUsername = memberRepository.existsByUsername(request.getUsername());

        if (isExistedByName || isExistedByUsername) {

            return new SignUpResponse(false);
        }

        Member member = Member.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER")
                .build();

        memberRepository.save(member);

        return new SignUpResponse(true);
    }
}
