package com.matdongsan.demo.service.articleservice;


import com.matdongsan.demo.dto.request.article.GetPreferredArticlesRequest;
import com.matdongsan.demo.dto.response.article.GetPreferredArticlesResponse;
import com.matdongsan.demo.mysql.domain.Member;
import com.matdongsan.demo.mysql.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    @Value("${python.server.uri}")
    private String articleServerURI;

    private final MemberRepository memberRepository;

    public ResponseEntity<GetPreferredArticlesResponse> getPreferredArticles(GetPreferredArticlesRequest request) {

        Member member = memberRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        RestTemplate restTemplate = new RestTemplate();
        List<Float> preferenceVector = member.getPreferenceVectorList();

        ResponseEntity<GetPreferredArticlesResponse> response = restTemplate.postForEntity(
                articleServerURI+"/get_preferred_articles",
                preferenceVector,
                GetPreferredArticlesResponse.class);

        return response;
    }
}
