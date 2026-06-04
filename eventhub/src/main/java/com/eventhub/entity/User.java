package com.eventhub.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.eventhub.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="user name is required")
	@Column(nullable=false)
	@Pattern(regexp="^[A-Za-z ]+$")
	private String name;
	
	@Email(message="enter the valid email")
	@Column(nullable=false,unique=true)
	private String email;
	
	@NotBlank(message = "Password is required")
	@Size(min=6,message="Password must be at least 6 characters")
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private Role role;
	
	@NotNull(message="Created date is required")
	@PastOrPresent(message="Created date cannot be in the future")
	private LocalDateTime createdAt;
	
	@OneToMany(mappedBy="organizer")
	private List<Event> events;
	
	@OneToMany(mappedBy="user")
	private List<Booking> bookings;

	public User(long id, @NotBlank(message = "Category name is required") @Pattern(regexp = "^[A-Za-Z ]+$") String name,
			@Email(message = "enter the valid email") String email,
			@NotBlank(message = "password is required") String password, Role role,
			@NotNull(message = "Created date is required") @PastOrPresent(message = "Created date cannot be in the future") LocalDateTime createdAt,
			List<Event> events, List<Booking> bookings) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
		this.createdAt = createdAt;
		this.events = events;
		this.bookings = bookings;
	}

	public User() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	
	@PrePersist
	public void prePersist() {
	    createdAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
	    return "User [id=" + id +
	            ", name=" + name +
	            ", email=" + email +
	            ", role=" + role +
	            ", createdAt=" + createdAt +
	            "]";
	}
	
	
	

}
