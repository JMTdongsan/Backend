package com.matdongsan.demo.mongodb.repository;

import com.matdongsan.demo.mongodb.domain.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    List<Chat> findAllByChatRoomId(String roomId);
}
