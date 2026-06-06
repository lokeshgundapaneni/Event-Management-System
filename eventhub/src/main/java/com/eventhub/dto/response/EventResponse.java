package com.eventhub.dto.response;

import java.time.LocalDate;

import com.eventhub.enums.EventStatus;

public class EventResponse {

	private Long id;
    private String title;
    private String venue;
    private LocalDate eventDate;
    private Double ticketPrice;
    private Integer availableSeats;
    private EventStatus status;
	public EventResponse() {
		super();
	}
	public EventResponse(Long id, String title, String venue, LocalDate eventDate, Double ticketPrice,
			Integer availableSeats, EventStatus status) {
		super();
		this.id = id;
		this.title = title;
		this.venue = venue;
		this.eventDate = eventDate;
		this.ticketPrice = ticketPrice;
		this.availableSeats = availableSeats;
		this.status = status;
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
	public EventStatus getStatus() {
		return status;
	}
	public void setStatus(EventStatus status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "EventResponse [id=" + id + ", title=" + title + ", venue=" + venue + ", eventDate=" + eventDate
				+ ", ticketPrice=" + ticketPrice + ", availableSeats=" + availableSeats + ", status=" + status + "]";
	}
    
    
}
