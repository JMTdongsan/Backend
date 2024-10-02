package com.matdongsan.demo.controller;

import com.matdongsan.demo.dto.request.chat.CreateChatRoomRequest;
import com.matdongsan.demo.dto.request.chat.GetAllChatsFromChatRoomRequest;
import com.matdongsan.demo.dto.request.openAi.OpenAiRequest;
import com.matdongsan.demo.dto.response.chat.CreateChatRoomResponse;
import com.matdongsan.demo.dto.response.chat.GetAllChatsFromChatRoomResponse;
import com.matdongsan.demo.dto.response.openAi.OpenAiResponse;
import com.matdongsan.demo.service.chatservice.ChatBotService;
import com.matdongsan.demo.service.chatservice.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Value("${openai.api.model}")
    private String openAiModel;

    @Value("${openai.api.url}")
    private String openAiApiUrl;

    private final RestTemplate openAiTemplate;
    private final ChatService chatService;
    private final ChatBotService chatBotService;

    public ChatController(@Qualifier("openAiTemplate") RestTemplate openAiTemplate,
                          ChatService chatService,
                          ChatBotService chatBotService) {
        this.openAiTemplate = openAiTemplate;
        this.chatService = chatService;
        this.chatBotService = chatBotService;
    }

    @GetMapping("/directMessage2openAiChatBot")
    public String directMessage2openAiChatBot(@RequestParam(name = "prompt")String prompt){
        OpenAiRequest request = new OpenAiRequest(openAiModel, prompt);
        OpenAiResponse openAiResponse = openAiTemplate.postForObject(openAiApiUrl, request, OpenAiResponse.class);
        return openAiResponse.getChoices().get(0).getMessage().getContent();
    }

    @GetMapping("/directMessage2ChatBot")
    public String directMessage2AiChatBot(@RequestParam(name = "prompt")String prompt) throws UnsupportedEncodingException {
        return chatBotService.getChatBotResponse(prompt);
    }

    @GetMapping("/getAllChatsFromChatRoom/{chatRoomId}")
    public ResponseEntity<GetAllChatsFromChatRoomResponse> getAllChatsFromChatRoom(
            @PathVariable("chatRoomId") String chatRoomId) {
        return ResponseEntity.ok(chatService.getAllChatsFromChatRoom(chatRoomId));
    }

    @PostMapping("/createChatRoom")
    public ResponseEntity<CreateChatRoomResponse> createChatRoom(@Valid @RequestBody CreateChatRoomRequest request) {
        return ResponseEntity.ok(chatService.createChatRoom(request));
    }
}
