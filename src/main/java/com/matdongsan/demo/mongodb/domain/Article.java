package com.matdongsan.demo.mongodb.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    private String id;
    private String content;
    private String url;
}
