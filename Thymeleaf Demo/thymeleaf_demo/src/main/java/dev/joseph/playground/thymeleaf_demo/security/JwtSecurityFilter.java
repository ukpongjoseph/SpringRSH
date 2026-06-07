package dev.joseph.playground.thymeleaf_demo.security;


import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import dev.joseph.playground.thymeleaf_demo.Service.MyUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtSecurityFilter extends OncePerRequestFilter{
    private JwtService jwtService;
    private MyUserDetailsService myUserDetailsService;
    public JwtSecurityFilter(JwtService service, MyUserDetailsService service2){
        this.jwtService = service;
        this.myUserDetailsService = service2;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        String userName = null;
        String token = extractTokenFromRequest(request);
        UsernamePasswordAuthenticationToken authToken = null;
        try {
            if (!token.isEmpty() && !jwtService.isTokenExpired(token) && token != null) {
                userName = jwtService.extractUserName(token);
                if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
                    UserDetails userDetails = myUserDetailsService.loadUserByUsername(userName);
                    if(jwtService.validateToken(token, userDetails)){
                        authToken = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }
            }
        } catch (Exception e) {
            filterChain.doFilter(request, response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    public String extractTokenFromRequest(HttpServletRequest request){
        String optionalHeader = request.getHeader("Authorization");
        Cookie[] optionalCookies = request.getCookies();
        if(optionalHeader != null && optionalHeader.startsWith("Bearer ")){
            String[] headerParts = optionalHeader.split(" ");
            if (headerParts.length == 2 && ("Bearer").equals(headerParts[0])) {
                String token = headerParts[1];
                return token;
            }
        }else if (optionalCookies != null) {
            for(Cookie cookie : optionalCookies){
                if (cookie.getName().equals("jwt")) {
                    String token = cookie.getValue();
                    return token;
                }
            }
        }
        return "";
    }
}
