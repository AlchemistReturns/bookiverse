package com.abrar.bookiverse.dto;

import lombok.Data;

@Data
public class RateBookRequest {
    private Long bookId;
    private Integer rating;
    private String summary;
}
