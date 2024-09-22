package com.matdongsan.demo.dto.request.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetAllChatsFromChatRoomRequest {

    private String chatRoomId;
    private String username;
}
