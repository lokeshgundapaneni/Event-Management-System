package com.eventhub.security.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;
    
//    this method will convert it into secret key object------>(Digital signature)
    private SecretKey getSigningKey()
    {
        return Keys.hmacShaKeyFor(
                secret.getBytes()
        );
    }
    
    
//    jwt token=header+payload+signature
    public String generateToken(String email,String role)
    {
		return Jwts.builder()
				.subject(email)
				.claim("role", role)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis()+expiration))
				.signWith(getSigningKey())
				.compact();
    }
    
//    helper method for extracting the payload details(email,expiry date,issued date)
    private Claims extractAllClaims(String token)
	{
		return Jwts.parser()
				.verifyWith(getSigningKey())
				.build()
	            .parseSignedClaims(token)
	            .getPayload();
	}
    
//    method for extracting the username
    public String extractUsername(String token)
    {
    	return extractAllClaims(token).getSubject();
    }
    
//  method for extracting the Role  
    public String extractRole(String token)
    {
        return extractAllClaims(token)
                .get("role", String.class);
    }
    
//    method for extracting the expiration date 
    public Date extractExpiration(String token)
    {
        return extractAllClaims(token).getExpiration();
    }
    
//    checking whether the token is expired or not by comparing with the present date
    public boolean isTokenExpired(String token)
    {
    	return extractExpiration(token).before(new Date());
    }
    
    
//    checking the token is valid using the received token and the email
    public boolean isTokenValid(String token,String email)
    {
    	String username=extractUsername(token);
    	return username.equals(email) && !isTokenExpired(token);
    }
    
    
    
    
    
    
    
}
