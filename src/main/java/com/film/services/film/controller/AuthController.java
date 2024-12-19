package com.film.services.film.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.film.services.film.dto.LoginUserDto;
import com.film.services.film.dto.RegisterUserDto;
import com.film.services.film.dto.response.LoginResponse;
import com.film.services.film.model.User;
import com.film.services.film.service.AuthService;
import com.film.services.film.service.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Auth", description = "Auth")
@RequestMapping("/auth")
@RestController
public class AuthController {
    final private AuthService authService;

    final private JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @Operation(
        summary = "Registrasi User",
        description = "Registrasi User",
        tags = { "Auth"},
        responses = {
				@ApiResponse( description = "Success", responseCode = "200"),
				@ApiResponse(description = "BadCredential", responseCode = "401", content = @Content),
				@ApiResponse(description = "Error", responseCode = "400", content = @Content),
				@ApiResponse(description = "Not Found", responseCode = "404", content = @Content)
			}
    )
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterUserDto rud){
        User u = authService.signup(rud);

        return ResponseEntity.ok().body(u);
    }

    @Operation(
        summary = "Login User",
        description = "Login User",
        tags = { "Auth"},
        responses = {
            @ApiResponse( description = "Success", responseCode = "200"),
            @ApiResponse(description = "BadCredential", responseCode = "401", content = @Content),
            @ApiResponse(description = "Error", responseCode = "400", content = @Content),
            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content)
        }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginUserDto lud){
        User u  = authService.authenticate(lud);

        String jwtToken = jwtService.generateToken(u);

        LoginResponse lr = new LoginResponse();
        lr.setToken(jwtToken);
        lr.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(lr);
    }
}
