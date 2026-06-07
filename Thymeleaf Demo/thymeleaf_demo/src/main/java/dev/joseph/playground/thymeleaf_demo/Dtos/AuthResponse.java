package dev.joseph.playground.thymeleaf_demo.Dtos;

public record AuthResponse(
        String firstName,
        String lastName,
        String email,
        String phoneNo,
        String role,
        String department,
        String token) {

}
