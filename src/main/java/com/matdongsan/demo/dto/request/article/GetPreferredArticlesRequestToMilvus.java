package com.matdongsan.demo.dto.request.article;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetPreferredArticlesRequestToMilvus {

    private String userPreferenceVector;
}
