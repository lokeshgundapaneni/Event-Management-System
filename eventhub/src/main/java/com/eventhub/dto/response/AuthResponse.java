package com.eventhub.dto.response;

public class AuthResponse {

	private String token;
    private UserResponse user;
	public AuthResponse(String token2, UserResponse userRes) {
		super();
		this.token = token2;
		this.user = userRes;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public UserResponse getUser() {
		return user;
	}
	public void setUser(UserResponse user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "AuthResponse [token=" + token + ", user=" + user + "]";
	}
	
	
}
