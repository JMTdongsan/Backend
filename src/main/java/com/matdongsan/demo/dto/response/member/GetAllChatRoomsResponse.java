package com.matdongsan.demo.dto.response.member;

import com.matdongsan.demo.mysql.domain.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetAllChatRoomsResponse {

    private List<ChatRoom> chatRooms;
}
