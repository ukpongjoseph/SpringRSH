package dev.joseph.playground.thymeleaf_demo.Dtos;

public record UserResponse(
        Integer id,
        String firstName,
        String lastName,
        String email,
        String phoneNo,
        String role,
        String department) {

}
