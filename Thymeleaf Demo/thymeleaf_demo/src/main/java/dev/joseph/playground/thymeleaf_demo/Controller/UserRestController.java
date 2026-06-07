package dev.joseph.playground.thymeleaf_demo.Controller;


import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.joseph.playground.thymeleaf_demo.Dtos.AuthRequest;
import dev.joseph.playground.thymeleaf_demo.Dtos.UserRequest;
import dev.joseph.playground.thymeleaf_demo.Service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;

@RestController
@RequestMapping(value = "/api/v1")
public class UserRestController {

    private AuthService service;
    
    public UserRestController(AuthService service){
        this.service = service;
    }
        
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserRequest registerRequestPayload) {
        return service.register(registerRequestPayload);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest loginRequestPayload, HttpServletResponse response) {
        String returnedToken = service.login(loginRequestPayload);
        ResponseCookie cookie =  ResponseCookie
                                    .from("jwt", returnedToken)
                                    .maxAge(3600)
                                    .httpOnly(true)
                                    .path("/myapp")
                                    .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.ok("Login Successful");
    }
}
