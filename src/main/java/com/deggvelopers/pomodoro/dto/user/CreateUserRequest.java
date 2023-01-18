package com.deggvelopers.pomodoro.dto.user;

public class CreateUserRequest {

    private String name;
    private String lastName;
    private String email;
    private String password;
    private String password2;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String name, String lastName, String email, String password, String password2) {
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.password2 = password2;
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

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
