package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagesDto {
    private Integer sendId;
    private Integer receivedId;
    private String sendName;
    private String receivedName;
    private String content;
}
