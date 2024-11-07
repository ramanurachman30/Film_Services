package com.film.services.film.dto.response;

import java.util.List;

import com.film.services.film.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private String code;
    private String message;
    private User data;
    private List<User> allData;
}
