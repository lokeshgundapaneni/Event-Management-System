package com.eventhub.entity;




import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.eventhub.enums.EventStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

@Entity
public class Event {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message="Title is required")
	@Column(nullable=false)
	private String title;
	
	@NotBlank(message="Description is required")
	@Column(nullable=false)
	private String description;
	
	@NotBlank(message="venue is required")
	@Column(nullable=false)
	private String venue;
	
	@Future(message="event date must be future")
	@Column(nullable=false)
	private LocalDate eventDate;
	
	@Positive(message="price must be positive")
	@Column(nullable=false)
	private Double ticketPrice;
	
	@Positive(message="available seats must be positive")
	@Column(nullable=false)
	private Integer availableSeats;
	
	
	private String imageUrl;
	
	@Column(nullable = false)
	private Integer remainingSeats;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private EventStatus status;
	
	@NotNull(message="Created date is required")
	@PastOrPresent(message="Created date cannot be in the future")
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name="organizer")
	private User organizer;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category; 
	
	@OneToMany(mappedBy="event")
	private List<Booking> bookings;
	
	public Event() {
		super();
	}

	public Event(Long id, @NotBlank(message = "Title is required") String title,
			@NotBlank(message = "Description is required") String description,
			@NotBlank(message = "venue is required") String venue,
			@Future(message = "event date must be future") LocalDate eventDate,
			@Positive(message = "price must be positive") Double ticketPrice,
			@Positive(message = "available seats must be positive") Integer availableSeats, String imageUrl,
			Integer remainingSeats, EventStatus status,
			@NotNull(message = "Created date is required") @PastOrPresent(message = "Created date cannot be in the future") LocalDateTime createdAt,
			User organizer, Category category, List<Booking> bookings) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.venue = venue;
		this.eventDate = eventDate;
		this.ticketPrice = ticketPrice;
		this.availableSeats = availableSeats;
		this.imageUrl = imageUrl;
		this.remainingSeats = remainingSeats;
		this.status = status;
		this.createdAt = createdAt;
		this.organizer = organizer;
		this.category = category;
		this.bookings = bookings;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getVenue() {
		return venue;
	}

	public void setVenue(String venue) {
		this.venue = venue;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public Double getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public Integer getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getRemainingSeats() {
		return remainingSeats;
	}

	public void setRemainingSeats(Integer remainingSeats) {
		this.remainingSeats = remainingSeats;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public User getOrganizer() {
		return organizer;
	}

	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}
	
	@PrePersist
	public void prePersist()
	{
	    createdAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
	    return "Event [id=" + id +
	            ", title=" + title +
	            ", description=" + description +
	            ", venue=" + venue +
	            ", eventDate=" + eventDate +
	            ", ticketPrice=" + ticketPrice +
	            ", availableSeats=" + availableSeats +
	            ", status=" + status +
	            ", createdAt=" + createdAt +
	            "]";
	}
	
}
