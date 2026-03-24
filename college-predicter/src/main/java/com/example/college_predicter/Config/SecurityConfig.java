package com.example.college_predicter.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests()

                // Permit specific public API endpoints without authentication

                // Allow POST to /api/BookDemo for booking a demo (your issue endpoint)
                .requestMatchers(HttpMethod.POST, "/api/BookDemo").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/College/CollegeRegister").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/{college_id}/uploadImage").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/College/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/College").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/College/{college_id}").permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/College/{college_id}").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/College/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/College/**").permitAll()


                // Allow all other requests under /api path
                .requestMatchers("/api/**").permitAll()

                // Allow POST to /api/BookDemo for booking a demo (your issue endpoint)
                .requestMatchers(HttpMethod.POST, "/api/BookDemo").permitAll()

                // Allow all demo bookings
                .requestMatchers(HttpMethod.GET, "/api/demoBookings").permitAll()
                .requestMatchers(HttpMethod.GET, "/demoBookings/college/{collegeId}").permitAll()



                // Any other request needs authentication
                .anyRequest().authenticated();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
