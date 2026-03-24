package com.example.college_predicter.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DemoBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long demo_id;


    private String studentName;


    private String studentEmail;


    private String collegeId;


    private String collegeName;

    private String demoPurpose;

    public DemoBooking() {}

    public DemoBooking(String studentName, String studentEmail, String collegeId, String collegeName, String demoPurpose) {
        this.studentName = studentName;
        this.studentEmail = studentEmail;
        this.collegeId = collegeId;
        this.collegeName = collegeName;
        this.demoPurpose = demoPurpose;
    }

    // Getters and setters...


    public Long getDemo_id() {
        return demo_id;
    }

    public void setDemo_id(Long demo_id) {
        this.demo_id = demo_id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentEmail() {
        return studentEmail;
    }

    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }

    public String getDemoPurpose() {
        return demoPurpose;
    }

    public void setDemoPurpose(String demoPurpose) {
        this.demoPurpose = demoPurpose;
    }
}
