package com.example.abdulhadichaudhry.abdurcloud;

public class User {
    private String photo;
    private String type;
    private String number;
    private String email;
    private String name;

    public User() {
    }

    public User(String photo, String type, String number, String email, String name) {
        this.photo = photo;
        this.type = type;
        this.number = number;
        this.email = email;
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
