package com.film.services.film.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
