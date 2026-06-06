package com.eventhub.dto.response;

import com.eventhub.enums.Role;

public class UserResponse {
	
	private Long id;
    private String name;
    private String email;
    private Role role;
    
    
	public UserResponse() {
		super();
	}
	public UserResponse(Long id, String name, String email, Role role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "UserResponse [id=" + id + ", name=" + name + ", email=" + email + ", role=" + role + "]";
	}
}
