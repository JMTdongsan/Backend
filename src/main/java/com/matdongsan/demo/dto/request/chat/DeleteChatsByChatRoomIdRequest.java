package com.matdongsan.demo.dto.request.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteChatsByChatRoomIdRequest {

    private String chatRoomId;
}
