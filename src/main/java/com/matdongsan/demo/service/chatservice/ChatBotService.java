package com.matdongsan.demo.service.chatservice;

import com.matdongsan.demo.dto.request.openAi.OpenAiRequest;
import com.matdongsan.demo.dto.request.openAi.SaveChatRequest;
import com.matdongsan.demo.dto.response.openAi.OpenAiResponse;
import com.matdongsan.demo.mongodb.domain.Chat;
import com.matdongsan.demo.mongodb.repository.ChatRepository;
import com.matdongsan.demo.mysql.domain.Member;
import com.matdongsan.demo.mysql.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatBotService {

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate template;
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    public String getChatbotResponse(String message){
        OpenAiRequest request = new OpenAiRequest(model, message);
        OpenAiResponse chatGPTResponse =  template.postForObject(apiUrl, request, OpenAiResponse.class);
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }

    public Chat saveChatMessage(SaveChatRequest request) {

        Member member = memberRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        Chat chat = Chat.builder()
                .id(String.valueOf(UUID.randomUUID()))
                .memberId(member.getMemberId())
                .chatRoomId(request.getChatRoomId())
                .role(request.getRole())
                .message(request.getMessage())
                .date(new Date().toString())
                .build();

        return chatRepository.save(chat);
    }
}
