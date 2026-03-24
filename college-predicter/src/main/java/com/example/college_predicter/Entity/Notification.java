//package com.example.college_predicter.Entity;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//
//@Entity
//public class Notification {
//
//    @Id
//
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id; // Primary key for Notification
//
//    private Long collegeId;    // Add this field if it doesn't exist
//    private String studentName;
//    private String studentEmail;
//    private String collegeName;
//    private String message;
//
//    // Getters and Setters
//
//    public Long getCollegeId() {
//        return collegeId;
//    }
//
//    public void setCollegeId(Long collegeId) {
//        this.collegeId = collegeId;
//    }
//
//    public String getStudentName() {
//        return studentName;
//    }
//
//    public void setStudentName(String studentName) {
//        this.studentName = studentName;
//    }
//
//    public String getStudentEmail() {
//        return studentEmail;
//    }
//
//    public void setStudentEmail(String studentEmail) {
//        this.studentEmail = studentEmail;
//    }
//
//    public String getCollegeName() {
//        return collegeName;
//    }
//
//    public void setCollegeName(String collegeName) {
//        this.collegeName = collegeName;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//}
