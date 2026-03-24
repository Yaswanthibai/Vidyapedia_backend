
package com.example.college_predicter.Entity;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class College
{

    @Id
    private String college_id;
    private String name;
    private String email;
    private String password;
    private long phone;
    private String city;
    private String college_type;
    private String[] courses = new String[10];
    private String[] services = new String[3];
    private String management ;
    @Column(length = 1000)
    private String description;



    @Lob
    private byte[] collegeImage;  // This stores the image as a byte array

    private String imageContentType;  // Store the content type (e.g., image/jpeg)


    public String getcollege_id() {
        return college_id;
    }

    public void setcollege_id(String college_id) {
        this.college_id = college_id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getcollege_type() {
        return college_type;
    }

    public void setcollege_type(String college_type) {
        this.college_type = college_type;
    }

    public String[] getCourses() {
        return courses;
    }

    public void setCourses(String[] courses) {
        this.courses = courses;
    }


    public String[] getServices() {
        return services;
    }

    public void setServices(String[] services) {
        this.services = services;
    }


    public String getManagement() {
        return management;
    }

    public void setManagement(String management) {
        this.management = management;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getCollegeImage() {
        return collegeImage;
    }

    public void setCollegeImage(byte[] collegeImage) {
        this.collegeImage = collegeImage;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    @Override
    public String toString() {
        return "College{" +
                "collegeId=" + college_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", city='" + city + '\'' +
                ", collegeType='" + college_type + '\'' +
                ", courses=" + courses +
                ", services=" + services +
                ", management='" + management + '\'' +
                ", description='" + description + '\'' +
                ", imageContentType='" + imageContentType + '\'' +
                '}';
    }
}














