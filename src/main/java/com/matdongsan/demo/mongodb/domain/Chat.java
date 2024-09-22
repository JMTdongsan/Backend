package com.matdongsan.demo.mongodb.domain;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "chat")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    private String id;
    private int memberId;
    private String chatRoomId;
    private String role;
    private String message;
    private String date;
}
