package com.abrar.bookiverse.dto;

import com.abrar.bookiverse.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class UserDashboardResponse {
    private Set<Book> readBooks;
    private Set<Book> currentlyReadingBooks;
    private Set<Book> planToReadBooks;
}
