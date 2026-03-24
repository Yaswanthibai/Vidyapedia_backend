package com.example.college_predicter.Reposetry;

import com.example.college_predicter.Entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepo extends JpaRepository<College, String>
{
}
