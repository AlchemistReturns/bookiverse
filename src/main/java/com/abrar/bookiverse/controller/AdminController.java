package com.abrar.bookiverse.controller;

import com.abrar.bookiverse.dto.ApproveBookRequest;
import com.abrar.bookiverse.dto.RejectBookRequest;
import com.abrar.bookiverse.entity.Book;
import com.abrar.bookiverse.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BookRepository bookRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/approve-book")
    public String approveBook(@RequestBody ApproveBookRequest request) {

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (book.isApproved()) {
            return "Book is already approved.";
        }

        book.setApproved(true);
        bookRepository.save(book);

        return "Book approved successfully!";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pending-books")
    public List<Book> getPendingBooks() {
        return bookRepository.findByApprovedFalse();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/reject-book")
    public String rejectBook(@RequestBody RejectBookRequest request) {
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookRepository.delete(book);

        return "Book rejected and deleted successfully.";
    }

}
