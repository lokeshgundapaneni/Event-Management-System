package com.eventhub.dto.response;

import java.time.LocalDate;

import com.eventhub.enums.EventStatus;

public class EventResponse {

	private Long id;
    private String title;
    private String description;
    private String venue;
    private LocalDate eventDate;
    private Double ticketPrice;
    private Integer availableSeats;
    private Integer remainingSeats;
    private String imageUrl;
    private EventStatus status;
    private String categoryName;
    private String organizerName;
    
	public EventResponse(Long id, String title, String description, String venue, LocalDate eventDate,
			Double ticketPrice, Integer availableSeats, Integer remainingSeats, String imageUrl, EventStatus status,
			String categoryName, String organizerName) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.venue = venue;
		this.eventDate = eventDate;
		this.ticketPrice = ticketPrice;
		this.availableSeats = availableSeats;
		this.remainingSeats = remainingSeats;
		this.imageUrl = imageUrl;
		this.status = status;
		this.categoryName = categoryName;
		this.organizerName = organizerName;
	}

	public EventResponse() {
		super();
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

	public Integer getRemainingSeats() {
		return remainingSeats;
	}

	public void setRemainingSeats(Integer remainingSeats) {
		this.remainingSeats = remainingSeats;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public EventStatus getStatus() {
		return status;
	}

	public void setStatus(EventStatus status) {
		this.status = status;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getOrganizerName() {
		return organizerName;
	}

	public void setOrganizerName(String organizerName) {
		this.organizerName = organizerName;
	}

	@Override
	public String toString() {
		return "EventResponse [id=" + id + ", title=" + title + ", description=" + description + ", venue=" + venue
				+ ", eventDate=" + eventDate + ", ticketPrice=" + ticketPrice + ", availableSeats=" + availableSeats
				+ ", remainingSeats=" + remainingSeats + ", imageUrl=" + imageUrl + ", status=" + status
				+ ", categoryName=" + categoryName + ", organizerName=" + organizerName + "]";
	}
	
	
}
