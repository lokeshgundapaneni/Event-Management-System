package com.eventhub.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.eventhub.security.jwt.JwtFilter;

@Configuration
public class SecurityConfig {
	
	private final JwtFilter jwtFilter;

	public SecurityConfig(JwtFilter jwtFilter)
	{
	    this.jwtFilter=jwtFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
	{
		http
			.csrf(customizer->customizer.disable())
			.sessionManagement(session ->
             				session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth->auth
					//public API's
					.requestMatchers("/auth/**")
					.permitAll()
					//categories
					.requestMatchers(HttpMethod.GET,"/categories/**")
					.authenticated()
					
					.requestMatchers(HttpMethod.POST,"/categories/**")
					.hasRole("ADMIN")
					
					.requestMatchers(HttpMethod.PUT,"/categories/**")
					.hasRole("ADMIN")
					
					.requestMatchers(HttpMethod.DELETE,"/categories/**")
					.hasRole("ADMIN")
					
					//Events
					
					.requestMatchers(HttpMethod.GET,"/events/**")
					.authenticated()
					
					.requestMatchers(HttpMethod.POST,"/events/**")
					.hasAnyRole("ADMIN","ORGANIZER")
					
					.requestMatchers(HttpMethod.PUT,"/events/**")
					.hasAnyRole("ADMIN","ORGANIZER")
					
					.requestMatchers(HttpMethod.DELETE,"/events/**")
					.hasAnyRole("ADMIN","ORGANIZER")
					
					//users
					.requestMatchers("/users/**")
				    .hasRole("ADMIN")
				    
				    //Booking
				    .requestMatchers("/bookings/**")
				    .hasAnyRole(
				            "USER",
				            "ORGANIZER",
				            "ADMIN"
				    )
					
					.anyRequest().authenticated())
			.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
			return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
	{
		return config.getAuthenticationManager();
	}
}
