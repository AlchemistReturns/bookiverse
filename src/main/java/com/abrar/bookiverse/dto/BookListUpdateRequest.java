package com.abrar.bookiverse.dto;

import lombok.Data;

@Data
public class BookListUpdateRequest {
    private Long bookId;
    private String listType; // "READ", "CURRENTLY_READING", "PLAN_TO_READ"
}
