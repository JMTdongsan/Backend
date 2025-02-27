package com.matdongsan.demo.configs.aiconfig.socketconfig;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matdongsan.demo.dto.request.openAi.SaveChatRequest;
import com.matdongsan.demo.service.chatservice.ChatBotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ChatBotService chatBotService;
    private final String chatBotErrorMessage = "요청중 오류가 발생했습니다. 다시 요청해주세요.";

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> payload = objectMapper.readValue(message.getPayload(), Map.class);
        String chatRoomId = (String) payload.get("chatRoomId");
        String username = (String) payload.get("username");
        String userMessage = (String) payload.get("message");

        log.info("User send message to chatRoom. / message: {} / username: {} / chatRoom: {}", userMessage, username, chatRoomId);

        SaveChatRequest userRequest = new SaveChatRequest(chatRoomId, "user", userMessage, username);
        chatBotService.saveChatMessage(userRequest);

        String responseMessage;

        try {
            responseMessage = chatBotService.getChatBotResponse(userMessage);
            log.info("Bot send message to chatRoom. / message: {} / chatRoom: {}", responseMessage, chatRoomId);
            SaveChatRequest chatBotRequest = new SaveChatRequest(chatRoomId, "bot", responseMessage, username);
            chatBotService.saveChatMessage(chatBotRequest);
            session.sendMessage(new TextMessage(responseMessage));
        } catch (Exception e) {
            log.info("Error: {}", e.toString());
            SaveChatRequest chatBotRequest = new SaveChatRequest(chatRoomId, "bot", chatBotErrorMessage, username);
            chatBotService.saveChatMessage(chatBotRequest);
            session.sendMessage(new TextMessage(chatBotErrorMessage));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
