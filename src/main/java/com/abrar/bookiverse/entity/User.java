package com.abrar.bookiverse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.List;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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

    @Enumerated(EnumType.STRING)
    private Role role;
    private String authProvider;

    @ElementCollection
    private List<String> preferredGenres;

    @ManyToMany
    @JoinTable(name = "user_read_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> readBooks;

    @ManyToMany
    @JoinTable(name = "user_currently_reading_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> currentlyReadingBooks;

    @ManyToMany
    @JoinTable(name = "user_plan_to_read_books",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "book_id"))
    private Set<Book> planToReadBooks;

}
