package com.matdongsan.demo.controller;

import com.matdongsan.demo.dto.request.article.GetPreferredArticlesRequest;
import com.matdongsan.demo.dto.request.article.GetSimilarArticlesRequest;
import com.matdongsan.demo.dto.request.article.GetSimilarArticlesRequestToMilvus;
import com.matdongsan.demo.dto.response.article.GetPreferredArticlesResponse;
import com.matdongsan.demo.dto.response.article.GetSimilarArticlesResponse;
import com.matdongsan.demo.dto.response.article.GetSomeArticlesResponse;
import com.matdongsan.demo.service.articleservice.ArticleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {


    @Value("${python.server.uri}")
    private String articleServerURI;

    private final ArticleService articleService;

    @GetMapping("/getSomeArticles")
    public ResponseEntity<GetSomeArticlesResponse> getSomeArticles() {

        return new RestTemplate().getForEntity(articleServerURI+"/get_some_articles", GetSomeArticlesResponse.class);
    }

    @PostMapping("/getSimilarArticles")
    public ResponseEntity<GetSimilarArticlesResponse> getSimilarArticles(@Valid @RequestBody GetSimilarArticlesRequest request) {

        return new RestTemplate().postForEntity(articleServerURI+"/get_similar_articles",
                new GetSimilarArticlesRequestToMilvus(request.getArticleId()),
                GetSimilarArticlesResponse.class);
    }

    @PostMapping("/getPreferredArticles")
    public ResponseEntity<GetPreferredArticlesResponse> getPreferredArticles(@Valid @RequestBody GetPreferredArticlesRequest request) {

        return articleService.getPreferredArticles(request);
    }
}
