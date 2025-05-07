package com.abrar.bookiverse.controller;

import com.abrar.bookiverse.dto.*;
import com.abrar.bookiverse.entity.Book;
import com.abrar.bookiverse.entity.User;
import com.abrar.bookiverse.entity.UserBook;
import com.abrar.bookiverse.repository.BookRepository;
import com.abrar.bookiverse.repository.UserBookRepository;
import com.abrar.bookiverse.repository.UserRepository;
import com.abrar.bookiverse.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final UserBookRepository userBookRepository;

    @GetMapping("/dashboard")
    public UserDashboardResponse getDashboard(@AuthenticationPrincipal UserPrincipal userPrincipal) {


        try {
            System.out.println("Inside getDashboard Controller");
            User user = userPrincipal.getUser();
            System.out.println("Fetched User: " + user.getName());

            return new UserDashboardResponse(
                    user.getReadBooks(),
                    user.getCurrentlyReadingBooks(),
                    user.getPlanToReadBooks()
            );
        } catch (Exception e) {
            e.printStackTrace(); // ✅ Print full stack trace
            throw e;
        }
    }

    @PostMapping("/add-currently-reading")
    public String addCurrentlyReading(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                      @RequestBody AddBookRequest request) {
        User user = userPrincipal.getUser();
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        user.getCurrentlyReadingBooks().add(book);

        userRepository.save(user);

        return "Book added to Currently Reading list!";
    }

    @PostMapping("/add-read")
    public String addRead(@AuthenticationPrincipal UserPrincipal userPrincipal,
                          @RequestBody AddBookRequest request) {
        User user = userPrincipal.getUser();
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        user.getReadBooks().add(book);
        userRepository.save(user);

        return "Book added to Read list!";
    }

    @PostMapping("/add-plan-to-read")
    public String addPlanToRead(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                @RequestBody AddBookRequest request) {
        User user = userPrincipal.getUser();
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        user.getPlanToReadBooks().add(book);
        userRepository.save(user);

        return "Book added to Plan to Read list!";
    }

    @PostMapping("/rate-book")
    public String rateBook(@AuthenticationPrincipal UserPrincipal userPrincipal,
                           @RequestBody RateBookRequest request) {

        User user = userRepository.findByEmail(userPrincipal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Check if user already rated this book
        Optional<UserBook> userBookOpt = userBookRepository.findByUserAndBook(user, book);

        UserBook userBook;
        if (userBookOpt.isPresent()) {
            userBook = userBookOpt.get(); // Update existing
        } else {
            userBook = new UserBook(); // New entry
            userBook.setUser(user);
            userBook.setBook(book);
        }

        userBook.setRating(request.getRating());
        userBook.setSummary(request.getSummary());

        userBookRepository.save(userBook);

        return "Rating and Summary saved successfully!";
    }

    @PostMapping("/add-book")
    public String addBookToList(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                @RequestBody BookListUpdateRequest request) {
        User user = userRepository.findByEmail(userPrincipal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        switch (request.getListType().toUpperCase()) {
            case "READ" -> {
                if (user.getReadBooks().contains(book)) {
                    return "Book is already in Read list.";
                }
                user.getReadBooks().add(book);
            }
            case "CURRENTLY_READING" -> {
                if (user.getCurrentlyReadingBooks().contains(book)) {
                    return "Book is already in Currently Reading list.";
                }
                user.getCurrentlyReadingBooks().add(book);
            }
            case "PLAN_TO_READ" -> {
                if (user.getPlanToReadBooks().contains(book)) {
                    return "Book is already in Plan to Read list.";
                }
                user.getPlanToReadBooks().add(book);
            }
            default -> throw new IllegalArgumentException("Invalid list type. Use READ, CURRENTLY_READING, or PLAN_TO_READ.");
        }

        userRepository.save(user);

        return "Book added to " + request.getListType() + " list.";
    }

    @PostMapping("/remove-book")
    public String removeBookFromList(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                     @RequestBody BookListUpdateRequest request) {
        User user = userRepository.findByEmail(userPrincipal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        switch (request.getListType().toUpperCase()) {
            case "READ" -> {
                if (!user.getReadBooks().contains(book)) {
                    return "Book is not in Read list.";
                }
                user.getReadBooks().remove(book);
            }
            case "CURRENTLY_READING" -> {
                if (!user.getCurrentlyReadingBooks().contains(book)) {
                    return "Book is not in Currently Reading list.";
                }
                user.getCurrentlyReadingBooks().remove(book);
            }
            case "PLAN_TO_READ" -> {
                if (!user.getPlanToReadBooks().contains(book)) {
                    return "Book is not in Plan to Read list.";
                }
                user.getPlanToReadBooks().remove(book);
            }
            default -> throw new IllegalArgumentException("Invalid list type. Use READ, CURRENTLY_READING, or PLAN_TO_READ.");
        }

        userRepository.save(user);

        return "Book removed from " + request.getListType() + " list.";
    }

    @PostMapping("/upload-book")
    public String uploadBook(@AuthenticationPrincipal UserPrincipal userPrincipal,
                             @RequestBody UploadBookRequest request) {

        Book book = Book.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .publisher(request.getPublisher())
                .isbn(request.getIsbn())
                .genre(request.getGenre())
                .averageRating(0.0)
                .approved(false) // Always false initially
                .build();

        bookRepository.save(book);

        return "Book uploaded successfully! Waiting for admin approval.";
    }

    @GetMapping("/profile")
    public UserProfileResponse getProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userRepository.findByEmail(userPrincipal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserProfileResponse(
                user.getName(),
                user.getAge(),
                user.getGender(),
                user.getNationality(),
                user.getEmail(),
                user.getPreferredGenres(),
                user.getReadBooks() != null ? user.getReadBooks().size() : 0,
                user.getCurrentlyReadingBooks() != null ? user.getCurrentlyReadingBooks().size() : 0,
                user.getPlanToReadBooks() != null ? user.getPlanToReadBooks().size() : 0
        );
    }

    @GetMapping("/book-details/{bookId}")
    public UserBookDetailsResponse getUserBookDetails(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                      @PathVariable Long bookId) {

        User user = userRepository.findByEmail(userPrincipal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserBook userBook = userBookRepository.findByUserAndBookId(user, bookId)
                .orElse(null);

        if (userBook == null) {
            return new UserBookDetailsResponse(null, null);  // No rating/summary yet
        }

        return new UserBookDetailsResponse(
                userBook.getRating(),
                userBook.getSummary()
        );
    }


}
