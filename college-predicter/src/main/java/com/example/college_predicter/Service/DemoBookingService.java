package com.example.college_predicter.Service;

import com.example.college_predicter.Entity.DemoBooking;
import com.example.college_predicter.Reposetry.DemoBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoBookingService {

    @Autowired
    private DemoBookingRepository demoBookingRepository;

    public DemoBooking createBooking(DemoBooking demoBooking) {
        return demoBookingRepository.save(demoBooking);
    }

    public List<DemoBooking> getAllBookings() {
        return demoBookingRepository.findAll();
    }


    // Other methods for creating and getting all bookings...

    public List<DemoBooking> getBookingsByCollegeId(String collegeId) {
        return demoBookingRepository.findByCollegeId(collegeId);


    }

}

