package com.matdongsan.demo.mongodb.domain;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "chat")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
    private Long id;
    private Long memberId;
    private String roomId;
    private String message;
    private String date;
}
