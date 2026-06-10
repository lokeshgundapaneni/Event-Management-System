package com.eventhub.dto.request;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class EventRequest {

	@Size(min = 3, max = 100, message = "Title must be between 3 and 100 characters")
	private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Venue is required")
    private String venue;

    @Future(message = "Event date must be in future")
    @NotNull(message = "Event date is required")
    private LocalDate eventDate;

    @Positive(message = "Ticket price must be positive")
    private Double ticketPrice;

    @Positive(message = "Available seats must be positive")
    private Integer availableSeats;
    
    
    // For now keep imageUrl
    private String imageUrl;

    @NotNull(message = "Category id is required")
    private Long categoryId;


    public EventRequest() {
    }

    

    public EventRequest(@NotBlank(message = "Title is required") String title,
			@NotBlank(message = "Description is required") String description,
			@NotBlank(message = "Venue is required") String venue,
			@Future(message = "Event date must be in future") @NotNull(message = "Event date is required") LocalDate eventDate,
			@Positive(message = "Ticket price must be positive") Double ticketPrice,
			@Positive(message = "Available seats must be positive") Integer availableSeats, String imageUrl,
			@NotNull(message = "Category id is required") Long categoryId
		) {
		super();
		this.title = title;
		this.description = description;
		this.venue = venue;
		this.eventDate = eventDate;
		this.ticketPrice = ticketPrice;
		this.availableSeats = availableSeats;
		this.imageUrl = imageUrl;
		this.categoryId = categoryId;
		
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}