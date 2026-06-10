package com.eventhub.security.config;

import java.util.List;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import com.eventhub.security.jwt.JwtFilter;

@Configuration
public class SecurityConfig {
	
	private final JwtFilter jwtFilter;

	public SecurityConfig(JwtFilter jwtFilter) {
	    this.jwtFilter = jwtFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(customizer -> customizer.disable())
			.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth
					
					// 1. Allow Pre-flight requests
					.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					
					// 2. Allow Swagger and error pages
					.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/error").permitAll()
					
					// 3. Public Auth APIs
					.requestMatchers("/auth/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/events/organizer/**").hasAnyRole("ADMIN", "ORGANIZER")
					
					// 4. Public Event/Category GET APIs
					.requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
					.requestMatchers(HttpMethod.GET, "/events/**").permitAll()
					
					// 5. Payment Verification Endpoints (MUST be permitted for Razorpay callback)
					.requestMatchers("/bookings/verify", "/bookings/verify-redirect").permitAll()
					
					// 6. Admin/Organizer restricted routes
					.requestMatchers(HttpMethod.POST, "/categories/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.PUT, "/categories/**").hasRole("ADMIN")
					.requestMatchers(HttpMethod.DELETE, "/categories/**").hasRole("ADMIN")
					
					.requestMatchers(HttpMethod.POST, "/events/**").hasAnyRole("ADMIN", "ORGANIZER")
					.requestMatchers(HttpMethod.PUT, "/events/**").hasAnyRole("ADMIN", "ORGANIZER")
					.requestMatchers(HttpMethod.DELETE, "/events/**").hasAnyRole("ADMIN", "ORGANIZER")
					
					// 7. Users
					.requestMatchers("/users/**").hasRole("ADMIN")
				    
				    // 8. Other Booking routes (requires authentication)
				    .requestMatchers("/bookings/**").hasAnyRole("USER", "ORGANIZER", "ADMIN")
					
					.anyRequest().authenticated())
			.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
			
			return http.build();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(List.of("http://localhost:3000")); 
	    configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Cache-Control"));
	    configuration.setAllowCredentials(true);
	    
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
}