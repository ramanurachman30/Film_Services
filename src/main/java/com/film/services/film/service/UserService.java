package com.film.services.film.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.film.services.film.model.User;
import com.film.services.film.repository.UserRepository;

@Service
public class UserService {
    final private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll(){
        List<User> u = new ArrayList<>();
        u = userRepository.findAll();
        return u;
    }

    public Optional<User> getUserById(Integer id){
        return userRepository.findById(id);
    }
}
