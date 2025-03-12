package com.example.lab5;

public class User {
    private String username;
    private String fullname;
    private String email;

    public User(String username, String fullname, String email) {
        this.username = username;
        this.fullname = fullname;
        this.email = email;
    }

    // Getters
    public String getUsername() { return username; }
    public String getFullname() { return fullname; }
    public String getEmail() { return email; }
}
