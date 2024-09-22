package com.matdongsan.demo.controller;

import com.matdongsan.demo.dto.request.chat.CreateChatRoomRequest;
import com.matdongsan.demo.dto.request.chat.GetAllChatsFromChatRoomRequest;
import com.matdongsan.demo.dto.request.openAi.OpenAiRequest;
import com.matdongsan.demo.dto.response.chat.CreateChatRoomResponse;
import com.matdongsan.demo.dto.response.chat.GetAllChatsFromChatRoomResponse;
import com.matdongsan.demo.dto.response.openAi.OpenAiResponse;
import com.matdongsan.demo.service.chatservice.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate template;
    private final ChatService chatService;

    @GetMapping("/directMessage2ChatBot")
    public String directMessage2ChatBot(@RequestParam(name = "prompt")String prompt){
        OpenAiRequest request = new OpenAiRequest(model, prompt);
        OpenAiResponse chatGPTResponse =  template.postForObject(apiUrl, request, OpenAiResponse.class);
        return chatGPTResponse.getChoices().get(0).getMessage().getContent();
    }

    @GetMapping("/getAllChatsFromChatRoom/{chatRoomId}")
    public ResponseEntity<GetAllChatsFromChatRoomResponse> getAllChatsFromChatRoom(
            @PathVariable String chatRoomId) {
        return ResponseEntity.ok(chatService.getAllChatsFromChatRoom(chatRoomId));
    }

    @PostMapping("/createChatRoom")
    public ResponseEntity<CreateChatRoomResponse> createChatRoom(@Valid @RequestBody CreateChatRoomRequest request) {
        return ResponseEntity.ok(chatService.createChatRoom(request));
    }
}
