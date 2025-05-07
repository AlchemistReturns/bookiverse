package com.abrar.bookiverse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserBookDetailsResponse {
    private Integer rating;  // 1 to 5
    private String summary;
}
