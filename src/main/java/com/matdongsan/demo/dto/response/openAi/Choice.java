package com.matdongsan.demo.dto.response.openAi;

import com.matdongsan.demo.dto.request.openAi.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choice {

    private int index;
    private Message message;
}
