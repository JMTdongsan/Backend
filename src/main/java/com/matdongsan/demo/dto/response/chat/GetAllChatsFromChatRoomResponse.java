package com.matdongsan.demo.dto.response.chat;

import com.matdongsan.demo.mongodb.domain.Chat;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllChatsFromChatRoomResponse {

    private List<Chat> chats;
}
