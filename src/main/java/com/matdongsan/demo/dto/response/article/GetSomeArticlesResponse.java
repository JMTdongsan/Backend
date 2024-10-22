package com.matdongsan.demo.dto.response.article;

import com.matdongsan.demo.mongodb.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetSomeArticlesResponse {

    private List<Article> articles;
}
