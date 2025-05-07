package com.abrar.bookiverse.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class UserBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    private Integer rating;  // e.g., 1 to 5

    @Column(length = 2000)
    private String summary; // Personal notes / thoughts
}
