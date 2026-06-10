package com.eventhub.security.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.eventhub.security.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
    
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    
    public JwtFilter(JwtUtil jwtUtil, CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
        throws ServletException, IOException {
        
        String path = request.getRequestURI();
        
        // Skip JWT filter for payment verification and public endpoints
        if (path.startsWith("/bookings/verify") || path.startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        // Only proceed if header exists and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authHeader.substring(7);
            String username = jwtUtil.extractUsername(token);
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                 UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                 
                 if (jwtUtil.isTokenValid(token, userDetails.getUsername())) {
                     UsernamePasswordAuthenticationToken authToken = 
                             new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                     
                     SecurityContextHolder.getContext().setAuthentication(authToken);
                 }
            }
        } catch (Exception e) {
            System.out.println("JWT Filter: Token is expired or invalid. " + e.getMessage());
            // We do not re-throw here to allow the SecurityFilterChain 
            // to handle the unauthorized access naturally
        }
        
        filterChain.doFilter(request, response);
    }
}