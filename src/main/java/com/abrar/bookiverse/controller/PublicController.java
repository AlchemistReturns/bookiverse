package com.abrar.bookiverse.controller;

import com.abrar.bookiverse.entity.Book;
import com.abrar.bookiverse.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final BookRepository bookRepository;

    @GetMapping("/search")
    public List<Book> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String genre
    ) {
        if (title == null && author == null && genre == null) {
            // If no filters, return all approved books
            return bookRepository.findByApprovedTrue();
        }

        return bookRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCaseAndApprovedTrue(
                title != null ? title : "",
                author != null ? author : "",
                genre != null ? genre : ""
        );
    }
}
