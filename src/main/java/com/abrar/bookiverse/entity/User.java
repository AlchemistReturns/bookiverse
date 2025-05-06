package com.abrar.bookiverse.entity;

import jakarta.persistence.*;

import java.util.List;

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private String nationality;
    private String email;
    private String password;

    @Enumerated
    private Role role;
    private String authProvider;

    @ElementCollection
    private List<String> preferredGenres;

    @ManyToMany
    @JoinTable(name = "user_read_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> readBooks;

    @ManyToMany
    @JoinTable(name = "user_currently_reading_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> currentlyReadingBooks;

    @ManyToMany
    @JoinTable(name = "user_plan_to_read_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private List<Book> planToReadBooks;

}
