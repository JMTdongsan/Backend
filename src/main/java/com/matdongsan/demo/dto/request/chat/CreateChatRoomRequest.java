package com.matdongsan.demo.dto.request.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateChatRoomRequest {

    private String username;
    private String title;
}
