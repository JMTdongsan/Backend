package com.matdongsan.demo.mysql.repository;

import com.matdongsan.demo.mysql.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Integer> {

    @Query("SELECT c FROM ChatRoom c WHERE c.creator.memberId=:memberId")
    List<ChatRoom> findAllByMemberId(@Param("memberId") int memberId);
}
