package com.matdongsan.demo.dto.request.openAi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaveChatRequest {

    String chatRoomId;
    String role;
    String message;
    String username;
}
