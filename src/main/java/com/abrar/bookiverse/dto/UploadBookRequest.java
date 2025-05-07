package com.abrar.bookiverse.dto;

import lombok.Data;

@Data
public class UploadBookRequest {
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String genre;
    // Skipping coverImage for now (can extend later)
}
