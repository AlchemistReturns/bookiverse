package com.abrar.bookiverse.controller;

import com.abrar.bookiverse.dto.AddBookRequest;
import com.abrar.bookiverse.dto.UserDashboardResponse;
import com.abrar.bookiverse.entity.Book;
import com.abrar.bookiverse.entity.User;
import com.abrar.bookiverse.repository.BookRepository;
import com.abrar.bookiverse.repository.UserRepository;
import com.abrar.bookiverse.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

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
}
