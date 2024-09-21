package com.matdongsan.demo.service;

import com.matdongsan.demo.mysql.domain.Member;
import com.matdongsan.demo.configs.jwt.CustomUserDetails;
import com.matdongsan.demo.mysql.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Member> memberOptional = memberRepository.findByUsername(username);

        if (memberOptional.isPresent()) {

            return new CustomUserDetails(memberOptional.get());
        }

        return null;
    }
}
