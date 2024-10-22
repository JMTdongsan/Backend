package com.matdongsan.demo.dto.request.article;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetSimilarArticlesRequestToMilvus {

    private String id;
}
