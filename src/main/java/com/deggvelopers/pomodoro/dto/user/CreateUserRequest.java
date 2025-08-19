package com.deggvelopers.pomodoro.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CreateUserRequest {

    private String name;
    private String lastName;
    private String email;
    private String password;
    private String password2;

    public CreateUserRequest() {
    }

}
