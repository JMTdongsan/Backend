package com.matdongsan.demo.dto.request.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdjustUserPreferenceRequest {

    private String username;
    private String articleId;
}
