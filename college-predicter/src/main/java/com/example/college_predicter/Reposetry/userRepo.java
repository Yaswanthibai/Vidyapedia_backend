package com.example.college_predicter.Reposetry;

import com.example.college_predicter.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepo extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email); // Add this line


}
