package com.matdongsan.demo.dto.response.article;

import com.matdongsan.demo.mongodb.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetArticleResponse {

    private Article article;
}
