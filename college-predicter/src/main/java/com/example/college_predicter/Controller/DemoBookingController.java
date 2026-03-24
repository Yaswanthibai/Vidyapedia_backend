package com.example.college_predicter.Controller;

import com.example.college_predicter.Entity.DemoBooking;
import com.example.college_predicter.Service.DemoBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class DemoBookingController {

    @Autowired
    private DemoBookingService demoBookingService;

    @PostMapping("/BookDemo")
    public ResponseEntity<String> bookDemo( @RequestBody DemoBooking demoBooking, BindingResult result) {

        // Check for validation errors
        if (result.hasErrors()) {
            return new ResponseEntity<>("Validation failed: " + result.getAllErrors(), HttpStatus.BAD_REQUEST);
        }

        // Save the demo booking
        demoBookingService.createBooking(demoBooking);
        return new ResponseEntity<>("Demo booking successful! We will contact you soon.", HttpStatus.OK);
    }

    // Get all bookings (optional, for testing)
    @GetMapping("/demoBookings")
    public ResponseEntity<?> getAllBookings() {
        return new ResponseEntity<>(demoBookingService.getAllBookings(), HttpStatus.OK);
    }


    // Get demo bookings by collegeId
    @GetMapping("/demoBookings/college/{collegeId}")
    public ResponseEntity<?> getBookingsByCollegeId(@PathVariable("collegeId") String collegeId) {
        List<DemoBooking> bookings = demoBookingService.getBookingsByCollegeId(collegeId);

        if (bookings.isEmpty()) {
            return new ResponseEntity<>("No demo bookings found for this college.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(bookings, HttpStatus.OK);
    }

}
