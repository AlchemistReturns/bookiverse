package com.abrar.bookiverse.repository;

import com.abrar.bookiverse.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u " +
            "LEFT JOIN FETCH u.readBooks " +
            "LEFT JOIN FETCH u.currentlyReadingBooks " +
            "LEFT JOIN FETCH u.planToReadBooks " +
            "WHERE u.email = :email")
    Optional<User> findByEmailFetchBooks(@Param("email") String email);
}
