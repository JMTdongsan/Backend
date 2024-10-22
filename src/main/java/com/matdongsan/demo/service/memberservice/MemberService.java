package com.matdongsan.demo.service.memberservice;

import com.matdongsan.demo.dto.request.article.GetArticleEmbedRequest;
import com.matdongsan.demo.dto.request.member.AdjustUserPreferenceRequest;
import com.matdongsan.demo.dto.request.member.GetAllChatRoomsRequest;
import com.matdongsan.demo.dto.response.article.GetArticleEmbedResponse;
import com.matdongsan.demo.dto.response.member.AdjustUserPreferenceResponse;
import com.matdongsan.demo.dto.response.member.GetAllChatRoomsResponse;
import com.matdongsan.demo.mysql.domain.ChatRoom;
import com.matdongsan.demo.mysql.domain.Member;
import com.matdongsan.demo.mysql.repository.ChatRoomRepository;
import com.matdongsan.demo.mysql.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    @Value("${python.server.uri}")
    private String articleServerURI;

    private final MemberRepository memberRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Transactional(readOnly = true)
    public GetAllChatRoomsResponse getAllChatRooms(GetAllChatRoomsRequest request) {

        Member member = memberRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        List<ChatRoom> allChatRooms = chatRoomRepository.findAllByMemberId(member.getMemberId());

        return new GetAllChatRoomsResponse(allChatRooms);
    }

    @Transactional
    public AdjustUserPreferenceResponse adjustUserPreference(AdjustUserPreferenceRequest request) {

        Member member = memberRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new RuntimeException("User not found")
        );


        RestTemplate restTemplate = new RestTemplate();

        GetArticleEmbedResponse response = restTemplate.postForEntity(
                articleServerURI + "/get_article_embed",
                new GetArticleEmbedRequest(request.getArticleId()),
                GetArticleEmbedResponse.class).getBody();

        List<Float> preferenceVector = member.getPreferenceVectorList();
        String[] articleEmbeddingVector = response.getArticleEmbed().split(",");

        for (int i = 0; i < preferenceVector.size(); i++) {
            float value = preferenceVector.get(i) - Float.parseFloat(articleEmbeddingVector[i])/20.0f;
            preferenceVector.set(i, value);
        }

        member.setPreferenceVector(preferenceVector);

        return new AdjustUserPreferenceResponse(true);
    }
}
