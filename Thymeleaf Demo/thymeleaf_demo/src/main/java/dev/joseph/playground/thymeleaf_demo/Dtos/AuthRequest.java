package dev.joseph.playground.thymeleaf_demo.Dtos;

public record AuthRequest(
    String email,
    String password
) {
    
}
