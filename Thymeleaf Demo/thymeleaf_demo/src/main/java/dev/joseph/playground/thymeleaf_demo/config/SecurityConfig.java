package dev.joseph.playground.thymeleaf_demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.joseph.playground.thymeleaf_demo.Service.MyUserDetailsService;
import dev.joseph.playground.thymeleaf_demo.security.JwtSecurityFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtSecurityFilter jwtSecurityFilter;

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http){
        http
            .securityMatcher("/api/v1/**")
            .csrf(customizer -> customizer.disable())
            .authorizeHttpRequests(
                request -> request
                        .requestMatchers("/api/v1/register", "/api/v1/login").permitAll()
                        .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            return http
                    .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain thymeleafSecurityFilterChain(HttpSecurity http){
        http
            .securityMatcher("/th/**")
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(
                request -> request
                        .requestMatchers("/th/register", "/th/login").permitAll()
                        .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            return http
                    .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }

    @Bean
    @Order(3)
    public SecurityFilterChain securityFilterChainDefault(HttpSecurity http){
        http
            .securityMatcher("/**")
            .authorizeHttpRequests(
                request -> request
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
            )
            .authenticationProvider(authenticationProvider())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
            return http
                    .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }

}
