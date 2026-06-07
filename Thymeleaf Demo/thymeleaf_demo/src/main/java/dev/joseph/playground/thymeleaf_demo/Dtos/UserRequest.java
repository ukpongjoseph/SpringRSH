package dev.joseph.playground.thymeleaf_demo.Dtos;


public record UserRequest(
     String firstName,
     String lastName,
     String email,
     String phoneNo,
     String department,
     String password
) {
    
}
