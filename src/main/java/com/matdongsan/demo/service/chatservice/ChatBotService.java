package com.matdongsan.demo.service.chatservice;

import com.matdongsan.demo.dto.request.openAi.OpenAiRequest;
import com.matdongsan.demo.dto.request.openAi.SaveChatRequest;
import com.matdongsan.demo.dto.response.ai.AiResponse;
import com.matdongsan.demo.dto.response.openAi.OpenAiResponse;
import com.matdongsan.demo.mongodb.domain.Chat;
import com.matdongsan.demo.mongodb.repository.ChatRepository;
import com.matdongsan.demo.mysql.domain.Member;
import com.matdongsan.demo.mysql.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class ChatBotService {

    @Value("${openai.api.model}")
    private String openAiModel;

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    @Value("${ai.url}")
    private String aiUrl;

    @Value("${ai.path}")
    private String aiPath;

    private final RestTemplate openAiTemplate;
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    public ChatBotService(@Qualifier("openAiTemplate") RestTemplate openAiTemplate,
                          ChatRepository chatRepository,
                          MemberRepository memberRepository) {
        this.openAiTemplate = openAiTemplate;
        this.chatRepository = chatRepository;
        this.memberRepository = memberRepository;
    }

    public String getOpenAiChatBotResponse(String message){
        OpenAiRequest request = new OpenAiRequest(openAiModel, message);
        OpenAiResponse chatGPTResponse = openAiTemplate.postForObject(openAiApiUrl, request, OpenAiResponse.class);
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }

    public String getChatBotResponse(String message) throws UnsupportedEncodingException {

        String encodedMessage = URLEncoder.encode(message, "UTF-8");

        URI uri = UriComponentsBuilder
                .fromUriString(aiUrl)
                .path(aiPath)
                .queryParam("question", encodedMessage)
                .build()
                .toUri();

        log.info("챗봇 요청 URI: {}", uri.toString());

        RestTemplate restTemplate = new RestTemplate();

        AiResponse aiResponse = restTemplate.getForEntity(uri, AiResponse.class).getBody();

        return aiResponse.getAnswer();
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
