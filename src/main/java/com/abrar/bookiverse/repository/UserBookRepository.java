package com.abrar.bookiverse.repository;

import com.abrar.bookiverse.entity.Book;
import com.abrar.bookiverse.entity.User;
import com.abrar.bookiverse.entity.UserBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBookRepository extends JpaRepository<UserBook, Long> {

    Optional<UserBook> findByUserAndBook(User user, Book book);

    Optional<UserBook> findByUserAndBookId(User user, Long bookId);
}
