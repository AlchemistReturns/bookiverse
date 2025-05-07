package com.abrar.bookiverse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class UserProfileResponse {
    private String name;
    private Integer age;
    private String gender;
    private String nationality;
    private String email;
    private List<String> preferredGenres;
    private int totalBooksRead;
    private int currentlyReading;
    private int plannedBooks;
}
