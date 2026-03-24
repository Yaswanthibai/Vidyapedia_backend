package com.example.college_predicter.Controller;

import com.example.college_predicter.Entity.College;
import com.example.college_predicter.Reposetry.CollegeRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.college_predicter.Util.JwtUtil;
import org.springframework.web.multipart.MultipartFile;


import java.util.Base64;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CollegeController {

    @Autowired
    CollegeRepo collegeRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private final JwtUtil jwtUtil;

    public CollegeController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }




    // Endpoint to fetch all colleges
    @GetMapping("/api/College")
    public ResponseEntity<List<College>> getAllColleges() {
        List<College> colleges = collegeRepo.findAll();
        return new ResponseEntity<>(colleges, HttpStatus.OK);
    }

//    // Endpoint to fetch a single college by ID
//    @GetMapping("/api/College/{college_id}")
//    public ResponseEntity<College> getCollege(@PathVariable String college_id) {
//        Optional<College> college = collegeRepo.findById(college_id);
//        return college.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }

    // Endpoint to update college information
    @PutMapping("/api/College/{college_id}")
    public ResponseEntity<College> updateCollege(@PathVariable String college_id, @RequestBody College collegeUpdatesForm) {
        System.out.println("Updating college with ID: " + college_id);

        // Check if the college exists
        Optional<College> collegeExistes = collegeRepo.findById(college_id);
        if (collegeExistes.isPresent()) {
            College college = collegeExistes.get();
            System.out.println("Before update: " + college);

            // Update fields
            college.setEmail(collegeUpdatesForm.getEmail());
            college.setPhone(collegeUpdatesForm.getPhone());
            college.setServices(collegeUpdatesForm.getServices());
            college.setManagement(collegeUpdatesForm.getManagement());
            college.setDescription(collegeUpdatesForm.getDescription());

            // Save updated college
            College updatedCollege = collegeRepo.save(college);
            System.out.println("After update: " + updatedCollege);

            return new ResponseEntity<>(updatedCollege, HttpStatus.OK);
        } else {
            System.out.println("College not found with ID: " + college_id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to delete a college by ID
    @DeleteMapping("/api/College/{college_id}")
    public ResponseEntity<Void> deleteCollege(@PathVariable String college_id) {
        Optional<College> collegeExistes = collegeRepo.findById(college_id);
        if (collegeExistes.isPresent()) {
            collegeRepo.deleteById(college_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint for College Login
    @PostMapping("/api/College/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody College loginRequest) {
        String college_id = loginRequest.getcollege_id();  // Ensure it uses the correct method name
        String password = loginRequest.getPassword();

        // Find college by ID
        Optional<College> collegeExists = collegeRepo.findById(college_id);
        Map<String, String> response = new HashMap<>();

        if (collegeExists.isPresent()) {
            College college = collegeExists.get();

            // Check if the password matches (using hashed password)
            if (passwordEncoder.matches(password, college.getPassword())) {
                // Generate JWT token
                String token = jwtUtil.generateToken(String.valueOf(college.getcollege_id()));

                // Prepare response with the token
                response.put("message", "Login successful");
                response.put("college", college.toString());
                response.put("token", token);  // Include the JWT token

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "Incorrect password");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } else {
            response.put("message", "College not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to register a college with an image and JSON data
    @PostMapping("/api/College/CollegeRegister")
    public ResponseEntity<Map<String, String>> registerCollege(
            @RequestPart("college") String collegeJson,  // Expecting the college JSON string as a part
            @RequestPart("image") MultipartFile image) { // Expecting the image as a part

        Map<String, String> response = new HashMap<>();

        // Deserialize the college JSON string into a College object
        ObjectMapper objectMapper = new ObjectMapper();
        College newCollege;
        try {
            newCollege = objectMapper.readValue(collegeJson, College.class);
        } catch (IOException e) {
            response.put("message", "Error parsing college data.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // Encrypt password before saving
        String encryptedPassword = passwordEncoder.encode(newCollege.getPassword());
        newCollege.setPassword(encryptedPassword);

        // Handle the image file upload
        try {
            byte[] imageBytes = image.getBytes();
            newCollege.setCollegeImage(imageBytes);  // Set the image as byte array
            newCollege.setImageContentType(image.getContentType());  // Set the image content type
        } catch (IOException e) {
            response.put("message", "Error uploading image.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Save the college in the database
        collegeRepo.save(newCollege);
        response.put("message", "College registered successfully.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @GetMapping("/api/College/{collegeId}")
    public ResponseEntity<Map<String, Object>> getCollegeById(@PathVariable("collegeId") String collegeId) {
        Map<String, Object> response = new HashMap<>();

        // Retrieve the college using the ID (assuming `collegeRepo` is the repository)
        College college = collegeRepo.findById(collegeId).orElse(null);

        if (college == null) {
            response.put("message", "College not found.");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        // Convert image byte array to Base64 string
        String base64Image = Base64.getEncoder().encodeToString(college.getCollegeImage());

        // Return college details along with the image content type and Base64 encoded image
        response.put("college", college);
        response.put("imageContentType", college.getImageContentType());  // Image content type (e.g., "image/jpeg")
        response.put("collegeImage", base64Image);  // College image in Base64 string

        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}





//
//    // Endpoint for College Registration with Image Upload
//    @PostMapping("/api/College/CollegeRegister")
//    public ResponseEntity<Map<String, String>> saveData(
//            @RequestParam("college") String collegeData,  // College data as a JSON string
//            @RequestParam("image") MultipartFile image  // Image file as part of the request
//    ) {
//        Map<String, String> response = new HashMap<>();
//
//        try {
//            // Convert the college data (JSON) into College object
//            // You can use Jackson ObjectMapper or a service to deserialize
//            // Assuming `collegeData` is a JSON string and you have a way to map it to a College entity
//
//            College newCollege = new ObjectMapper().readValue(collegeData, College.class);
//
//            // Encrypt password
//            String encryptedPassword = passwordEncoder.encode(newCollege.getPassword());
//            newCollege.setPassword(encryptedPassword);
//
//            // Convert image to byte array and set it in the College entity
//            byte[] imageBytes = image.getBytes();
//            newCollege.setCollegeImage(imageBytes);
//            newCollege.setImageContentType(image.getContentType());
//
//            // Save the new college data to the database
//            collegeRepo.save(newCollege);
//
//            response.put("message", "College registered successfully with image.");
//            return new ResponseEntity<>(response, HttpStatus.CREATED);
//
//        } catch (IOException e) {
//            response.put("message", "Error processing the image or data.");
//            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }














//
//package com.example.college_predicter.Controller;
//
//import com.example.college_predicter.Entity.College;
//import com.example.college_predicter.Reposetry.CollegeRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.*;
//import com.example.college_predicter.Util.JwtUtil;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//@RestController
//public class CollegeController {
//
//    @Autowired
//    CollegeRepo collegeRepo;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private final JwtUtil jwtUtil;
//
//    public CollegeController(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//    // Endpoint for College Registration
//    @PostMapping("/api/College/CollegeRegister")
//    public ResponseEntity<College> saveData(@RequestBody College newCollege) {
//        System.out.println("Received College: " + newCollege);
//
//        // Encrypt password
//        String encryptedPassword = passwordEncoder.encode(newCollege.getPassword());
//        newCollege.setPassword(encryptedPassword);
//
//        // Save college in the database
//        College savedCollege = collegeRepo.save(newCollege);
//        return new ResponseEntity<>(savedCollege, HttpStatus.CREATED);
//    }
//
//    // Endpoint to fetch all colleges
//    @GetMapping("/api/College")
//    public ResponseEntity<List<College>> getAllColleges() {
//        List<College> colleges = collegeRepo.findAll();
//        return new ResponseEntity<>(colleges, HttpStatus.OK);
//    }
//
//    // Endpoint to fetch a single college by ID
//    @GetMapping("/api/College/{college_id}")
//    public ResponseEntity<College> getCollege(@PathVariable String college_id) {
//        Optional<College> college = collegeRepo.findById(college_id);
//        return college.map(c -> new ResponseEntity<>(c, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    // Endpoint to update college information
//    @PutMapping("/api/College/{college_id}")
//    public ResponseEntity<College> updateCollege(@PathVariable String college_id, @RequestBody College collegeUpdatesForm) {
//        System.out.println("Updating college with ID: " + college_id);
//
//        // Check if the college exists
//        Optional<College> collegeExistes = collegeRepo.findById(college_id);
//        if (collegeExistes.isPresent()) {
//            College college = collegeExistes.get();
//            System.out.println("Before update: " + college);
//
//            // Update fields
//            college.setEmail(collegeUpdatesForm.getEmail());
//            college.setPhone(collegeUpdatesForm.getPhone());
//            college.setServices(collegeUpdatesForm.getServices());
//            college.setManagement(collegeUpdatesForm.getManagement());
//            college.setDescription(collegeUpdatesForm.getDescription());
//
//            // Save updated college
//            College updatedCollege = collegeRepo.save(college);
//            System.out.println("After update: " + updatedCollege);
//
//            return new ResponseEntity<>(updatedCollege, HttpStatus.OK);
//        } else {
//            System.out.println("College not found with ID: " + college_id);
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Endpoint to delete a college by ID
//    @DeleteMapping("/api/College/{college_id}")
//    public ResponseEntity<Void> deleteCollege(@PathVariable String college_id) {
//        Optional<College> collegeExistes = collegeRepo.findById(college_id);
//        if (collegeExistes.isPresent()) {
//            collegeRepo.deleteById(college_id);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Endpoint for College Login
//    @PostMapping("/api/College/login")
//    public ResponseEntity<Map<String, String>> login(@RequestBody College loginRequest) {
//        String college_id = loginRequest.getcollege_id();  // Ensure it uses the correct method name
//        String password = loginRequest.getPassword();
//
//        // Find college by ID
//        Optional<College> collegeExists = collegeRepo.findById(college_id);
//        Map<String, String> response = new HashMap<>();
//
//        if (collegeExists.isPresent()) {
//            College college = collegeExists.get();
//
//            // Check if the password matches (using hashed password)
//            if (passwordEncoder.matches(password, college.getPassword())) {
//                // Generate JWT token
//                String token = jwtUtil.generateToken(String.valueOf(college.getcollege_id()));
//
//                // Prepare response with the token
//                response.put("message", "Login successful");
//                response.put("college", college.toString());
//                response.put("token", token);  // Include the JWT token
//
//                return new ResponseEntity<>(response, HttpStatus.OK);
//            } else {
//                response.put("message", "Incorrect password");
//                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//            }
//        } else {
//            response.put("message", "College not found");
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//
//    // Endpoint to upload college image
//    @PostMapping("/api/{college_id}/uploadImage")
//    public ResponseEntity<Map<String, String>> uploadImage(@PathVariable String college_id,
//                                                           @RequestParam("image") MultipartFile image) {
//        Map<String, String> response = new HashMap<>();
//
//        // Find college by ID
//        Optional<College> collegeOptional = collegeRepo.findById(college_id);
//        if (collegeOptional.isPresent()) {
//            College college = collegeOptional.get();
//
//            try {
//                // Convert image to byte array
//                byte[] imageBytes = image.getBytes();
//
//                // Set image and its content type in the    college entity
//                college.setCollegeImage(imageBytes);
//                college.setImageContentType(image.getContentType());
//
//                // Save the college with the new image
//                collegeRepo.save(college);
//
//                response.put("message", "Image uploaded successfully");
//                return new ResponseEntity<>(response, HttpStatus.OK);
//
//            } catch (IOException e) {
//                response.put("message", "Error uploading image");
//                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//            }
//        } else {
//            response.put("message", "College not found");
//            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
//        }
//    }
//
//    // Endpoint to get the image of a college
//    @GetMapping("/api/{college_id}/getImage")
//    public ResponseEntity<byte[]> getImage(@PathVariable String college_id) {
//        Optional<College> collegeOptional = collegeRepo.findById(college_id);
//
//        if (collegeOptional.isPresent()) {
//            College college = collegeOptional.get();
//            byte[] image = college.getCollegeImage();
//
//            if (image != null) {
//                // Set the correct content type based on the image's content type
//                return ResponseEntity.ok()
//                        .header("Content-Type", college.getImageContentType())
//                        .body(image);
//            } else {
//                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//            }
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//
//
//}
