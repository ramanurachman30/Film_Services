package com.film.services.film.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.film.services.film.constant.GetConstant;
import com.film.services.film.dto.response.UserResponse;
import com.film.services.film.model.User;
import com.film.services.film.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Informasi User", description = "Informasi User")
@RequestMapping(GetConstant.USER)
@RestController
public class UserController {
    final private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
        summary = "Get SelfUser",
        description = "Get SelfUser Yang sudah terdaftar User",
        tags = { "Informasi User"},
        responses = {
				@ApiResponse( description = "Success", responseCode = "200"),
				@ApiResponse(description = "BadCredential", responseCode = "401", content = @Content),
				@ApiResponse(description = "Error", responseCode = "400", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content)
			}
    )
    @GetMapping(GetConstant.USER_GET_ME)
    public ResponseEntity<User> authenticate(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User currnUser = (User) auth.getPrincipal();

        System.out.println("MASUK KESINI GA ? " + currnUser);

        return ResponseEntity.ok(currnUser);
    }

    @Operation(
        summary = "Get Semua User",
        description = "Get Semua User Yang sudah terdaftar User",
        tags = { "Informasi User"},
        responses = {
            @ApiResponse( description = "Success", responseCode = "200"),
            @ApiResponse(description = "BadCredential", responseCode = "401", content = @Content),
            @ApiResponse(description = "Error", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content)
        }
    )
    @GetMapping(GetConstant.USER_GET_ALL)
    public ResponseEntity<UserResponse> getAll(){
        try {
            List<User> u = userService.getAll();
    
            UserResponse ur = new UserResponse();
            ur.setCode("200");
            ur.setMessage("Successfully");
            ur.setAllData(u);

            return new ResponseEntity<>(ur, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            UserResponse ur = new UserResponse();
            ur.setCode("500");
            ur.setMessage(e.getMessage());

            return new ResponseEntity<>(ur, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(
        summary = "Get User by id",
        description = "Get User Berdasarkan Id Yang sudah terdaftar User",
        tags = { "Informasi User"},
        responses = {
            @ApiResponse( description = "Success", responseCode = "200"),
            @ApiResponse(description = "BadCredential", responseCode = "401", content = @Content),
            @ApiResponse(description = "Error", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content)
        }
    )
    @GetMapping(GetConstant.USER_GET_BY_ID)
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Integer id){
        try {
            Optional<User> u = userService.getUserById(id);

            if (!u.isPresent()) {
                UserResponse ur = new UserResponse();
                ur.setCode("200");
                ur.setMessage("User not found");
                ur.setData(u.get());

                return new ResponseEntity<>(ur, HttpStatus.NOT_FOUND);
            }

            UserResponse ur = new UserResponse();
            ur.setCode("200");
            ur.setMessage("Successfully");
            ur.setData(u.get());

            return new ResponseEntity<>(ur, HttpStatus.OK);
        } catch (Exception e) {
            // TODO: handle exception
            UserResponse ur = new UserResponse();
            ur.setCode("500");
            ur.setMessage(e.getMessage());

            return new ResponseEntity<>(ur, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
