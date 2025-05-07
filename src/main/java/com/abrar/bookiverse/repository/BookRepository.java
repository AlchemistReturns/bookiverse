package com.abrar.bookiverse.repository;

import com.abrar.bookiverse.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);

    // Find all approved books
    List<Book> findByApprovedTrue();

    // Find by optional filters
    List<Book> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndGenreContainingIgnoreCaseAndApprovedTrue(
            String title, String author, String genre
    );

    List<Book> findByApprovedFalse();

}
