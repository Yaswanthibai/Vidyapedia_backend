package com.example.college_predicter.Entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int registration_id;
    private String name;
    private String email;
    private String password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRegistration_id() {
        return registration_id;
    }

    public void setRegistration_id(int registration_id) {
        this.registration_id = registration_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() { // Getter for password
        return password;
    }

    public void setPassword(String password) { // Setter for password
        this.password = password;
    }


    public User(String name, String email,String password, int registration_id) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.registration_id = registration_id;
    }

    public User() {
        super();
    }

    @Override
    public String toString() {
        return "User{" +
                "registration_id=" + registration_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
