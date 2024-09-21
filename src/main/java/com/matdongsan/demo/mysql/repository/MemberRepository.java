package com.matdongsan.demo.mysql.repository;

import com.matdongsan.demo.mysql.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Boolean existsByUsername(String username);
    Boolean existsByName(String name);
    Optional<Member> findByUsername(String username);
}
