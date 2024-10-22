package com.matdongsan.demo.controller;

import com.matdongsan.demo.dto.request.article.GetPreferredArticlesRequest;
import com.matdongsan.demo.dto.request.article.GetSimilarArticlesRequest;
import com.matdongsan.demo.dto.response.article.GetSimilarArticlesResponse;
import com.matdongsan.demo.dto.response.article.GetSomeArticlesResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/article")
@Slf4j
public class ArticleController {


    @Value("${python.server.uri}")
    private String articleServerURI;

    @GetMapping("/getSomeArticles")
    public ResponseEntity<GetSomeArticlesResponse> getSomeArticles() {

        return new RestTemplate().getForEntity(articleServerURI+"/get_some_articles", GetSomeArticlesResponse.class);
    }

    @PostMapping("/getSimilarArticles")
    public ResponseEntity<GetSimilarArticlesResponse> getSimilarArticles(@Valid @RequestBody GetSimilarArticlesRequest request) {

        return new RestTemplate().postForEntity(articleServerURI+"/get_similar_articles", request, GetSimilarArticlesResponse.class);
    }

//    @PostMapping("/getReferredArticles")
//    public ResponseEntity<GetPreferredArticlesRequest> getPreferredArticles(@Valid @RequestBody GetPreferredArticlesRequest request) {
//
//        return new ResponseEntity.ok();
//    }
}
