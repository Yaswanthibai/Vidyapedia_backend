package com.example.college_predicter.Controller;
import com.example.college_predicter.Entity.User;
import com.example.college_predicter.Reposetry.userRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.*;
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowedHeaders = "*", methods = {RequestMethod.POST, RequestMethod.OPTIONS}) // Allow requests from localhost:3000

public class userController
{
    @Autowired
    userRepo userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;



    // Registration endpoint with email validation
    @PostMapping("/api/user/register")
    public ResponseEntity<Map<String, String>> saveData(@RequestBody User newUser) {
        Map<String, String> response = new HashMap<>();

        // Check if email already exists in the database
        Optional<User> existingUser = userRepo.findByEmail(newUser.getEmail());

        if (existingUser.isPresent()) {
            // If the email already exists, return an error message
            response.put("message", "Email is already in use");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        // If email does not exist, encrypt password and save the new user
        String encryptedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encryptedPassword);

        // Save the user to the repository
        userRepo.save(newUser);

        // Respond with a success message
        response.put("message", "User registered successfully");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getAllUsers()
    {
        return new ResponseEntity<>(userRepo.findAll(), HttpStatus.OK);
    }


    @PutMapping("/api/user/{registration_id}")
    public ResponseEntity<User> updateUser(@PathVariable int registration_id, @RequestBody User UserUpdatesForm)
    {
        Optional<User> userExistes = userRepo.findById(registration_id);
        if(userExistes.isPresent())
        {
            System.out.println(userExistes);
            System.out.println(userExistes.get());
            userExistes.get().setName(UserUpdatesForm.getName());
            userExistes.get().setEmail(UserUpdatesForm.getEmail());

            return new ResponseEntity<>(userRepo.save(userExistes.get()), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/user/{registration_id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int registration_id)
    {
        Optional<User> userExistes = userRepo.findById(registration_id);
        if(userExistes.isPresent())
        {
            userRepo.deleteById(registration_id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/login")
    public String Login(@RequestBody User user){
        System.out.println(user);
        return "Success";
    }

    @PostMapping("/api/user/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        // Find user by email
        Optional<User> userExists = userRepo.findByEmail(email);
        Map<String, String> response = new HashMap<>(); // Create a response map


        if (userExists.isPresent()) {
            User user = userExists.get();

            if (passwordEncoder.matches(password, user.getPassword())) {
                response.put("message", "Login successful");
                response.put("user", user.toString());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "Incorrect password");
                return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
            }
        } else {
            response.put("message", "User not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
