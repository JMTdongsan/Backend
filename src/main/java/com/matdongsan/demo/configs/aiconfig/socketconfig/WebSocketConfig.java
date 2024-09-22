package com.matdongsan.demo.configs.aiconfig.socketconfig;

import com.matdongsan.demo.service.chatservice.ChatBotService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatBotService chatBotService;

    @Bean
    public ChatHandler chatHandler() {
        return new ChatHandler(chatBotService);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(chatHandler(), "/chat/sendMessage")
                .setAllowedOrigins("*");
    }
}
