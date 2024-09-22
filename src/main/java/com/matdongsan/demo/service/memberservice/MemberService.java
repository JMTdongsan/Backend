package com.matdongsan.demo.service.memberservice;

import com.matdongsan.demo.dto.request.member.GetAllChatRoomsRequest;
import com.matdongsan.demo.dto.response.member.GetAllChatRoomsResponse;
import com.matdongsan.demo.mysql.domain.ChatRoom;
import com.matdongsan.demo.mysql.domain.Member;
import com.matdongsan.demo.mysql.repository.ChatRoomRepository;
import com.matdongsan.demo.mysql.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional(readOnly = true)
    public GetAllChatRoomsResponse getAllChatRooms(GetAllChatRoomsRequest request) {

        Member member = memberRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        List<ChatRoom> allChatRooms = chatRoomRepository.findAllByMemberId(member.getMemberId());

        return new GetAllChatRoomsResponse(allChatRooms);
    }
}
