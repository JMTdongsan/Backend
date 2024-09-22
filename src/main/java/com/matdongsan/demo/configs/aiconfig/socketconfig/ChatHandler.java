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

@RequiredArgsConstructor
public class ChatHandler extends TextWebSocketHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final ChatBotService chatBotService;

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

        log.info("user send message to chatRoom({}) / message: {} / username: {}", chatRoomId, userMessage, username);

        SaveChatRequest userRequest = new SaveChatRequest(chatRoomId, "user", userMessage, username);
        chatBotService.saveChatMessage(userRequest);

        String responseMessage = chatBotService.getChatbotResponse(userMessage);

        log.info("bot send message to chatRoom({}) / message: {}", chatRoomId, responseMessage);

        SaveChatRequest chatBotRequest = new SaveChatRequest(chatRoomId, "bot", responseMessage, username);
        chatBotService.saveChatMessage(chatBotRequest);

        session.sendMessage(new TextMessage(responseMessage));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
