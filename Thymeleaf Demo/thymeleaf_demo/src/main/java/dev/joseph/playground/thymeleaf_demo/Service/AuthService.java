package dev.joseph.playground.thymeleaf_demo.Service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.joseph.playground.thymeleaf_demo.Dtos.AuthRequest;
import dev.joseph.playground.thymeleaf_demo.Dtos.UserRequest;
import dev.joseph.playground.thymeleaf_demo.Mapper.UserMapper;
import dev.joseph.playground.thymeleaf_demo.Model.User;
import dev.joseph.playground.thymeleaf_demo.Repo.UserRepo;
import dev.joseph.playground.thymeleaf_demo.security.JwtService;

@Service
public class AuthService {
    private UserRepo userRepo;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private MyUserDetailsService myUserDetailsService;

    public AuthService(UserRepo repo, UserMapper mapper, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtService jwtService, MyUserDetailsService myUserDetailsService){
        this.userMapper = mapper;
        this.userRepo = repo;
        this.passwordEncoder = encoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.myUserDetailsService = myUserDetailsService;
    }

    public ResponseEntity<String> register(UserRequest request){
        User userToBeSaved = userMapper.userRequestToUserModel(request);
        if (userRepo.existsByEmail(userToBeSaved.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Users already Exist");
        }
        userToBeSaved.setPassword(passwordEncoder.encode(userToBeSaved.getPassword()));
        userRepo.save(userToBeSaved);
        return ResponseEntity.status(HttpStatus.CREATED).body("Registration Successful");
    }

    public String login(AuthRequest request){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        Authentication authentication = authenticationManager.authenticate(authToken);
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);
        return token;

    }
}
