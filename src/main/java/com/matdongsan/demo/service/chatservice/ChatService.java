package com.matdongsan.demo.service.chatservice;

import com.matdongsan.demo.dto.request.chat.CreateChatRoomRequest;
import com.matdongsan.demo.dto.request.chat.GetAllChatsFromChatRoomRequest;
import com.matdongsan.demo.dto.response.chat.CreateChatRoomResponse;
import com.matdongsan.demo.dto.response.chat.GetAllChatsFromChatRoomResponse;
import com.matdongsan.demo.mongodb.domain.Chat;
import com.matdongsan.demo.mongodb.repository.ChatRepository;
import com.matdongsan.demo.mysql.domain.ChatRoom;
import com.matdongsan.demo.mysql.domain.Member;
import com.matdongsan.demo.mysql.repository.ChatRoomRepository;
import com.matdongsan.demo.mysql.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public CreateChatRoomResponse createChatRoom(CreateChatRoomRequest request) {

        Member creator = memberRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(String.valueOf(UUID.randomUUID()))
                .creator(creator)
                .title(request.getTitle())
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        log.info("User makes chatRoom. / username: {} / chatRoom: {}", creator.getUsername(), chatRoom);

        return new CreateChatRoomResponse(savedChatRoom.getChatRoomId());
    }

    @Transactional(readOnly = true)
    public GetAllChatsFromChatRoomResponse getAllChatsFromChatRoom(String chatRoomId) {

        List<Chat> chats = chatRepository.findAllByChatRoomId(chatRoomId);
        return new GetAllChatsFromChatRoomResponse(chats);
    }
}
