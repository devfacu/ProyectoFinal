package com.deggvelopers.pomodoro.dto.user;

public class userDTO {

    private String name;
    private String lastName;
    private String email;
    private String password;
    private String getPassword2;

    public userDTO() {
    }

    public userDTO(String name, String lastName, String email, String password, String getPassword2) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.getPassword2 = getPassword2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getGetPassword2() {
        return getPassword2;
    }

    public void setGetPassword2(String getPassword2) {
        this.getPassword2 = getPassword2;
    }
}
