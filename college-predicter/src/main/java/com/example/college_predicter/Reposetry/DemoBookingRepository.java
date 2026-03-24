package com.example.college_predicter.Reposetry;


import com.example.college_predicter.Entity.DemoBooking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemoBookingRepository extends JpaRepository<DemoBooking, String> {

    /// Custom query to find demo bookings by collegeId
    List<DemoBooking> findByCollegeId(String collegeId);
}


