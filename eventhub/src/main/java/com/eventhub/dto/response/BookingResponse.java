package com.eventhub.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.eventhub.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

public class BookingResponse {
	
	private Long id;
    private Long eventId;
    private String eventTitle;
    private String venue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate eventDate;
    private Integer ticketsCount;
    private Double ticketPrice;
    private Double totalAmount;
    private String razorpayOrderId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime bookingDate;
    private BookingStatus status;
	
    
    
    
    public BookingResponse(Long id, Long eventId, String eventTitle, String venue, LocalDate eventDate,
			Integer ticketsCount, Double ticketPrice, Double totalAmount, String razorpayOrderId,
			LocalDateTime bookingDate, BookingStatus status) {
		super();
		this.id = id;
		this.eventId = eventId;
		this.eventTitle = eventTitle;
		this.venue = venue;
		this.eventDate = eventDate;
		this.ticketsCount = ticketsCount;
		this.ticketPrice = ticketPrice;
		this.totalAmount = totalAmount;
		this.razorpayOrderId = razorpayOrderId;
		this.bookingDate = bookingDate;
		this.status = status;
	}

	public BookingResponse() {
		super();
	}
	
	public String getRazorpayOrderId() {
	    return razorpayOrderId;
	}

	public void setRazorpayOrderId(String razorpayOrderId) {
	    this.razorpayOrderId = razorpayOrderId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getEventId() {
		return eventId;
	}
	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}
	public String getEventTitle() {
		return eventTitle;
	}
	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
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
	public Integer getTicketsCount() {
		return ticketsCount;
	}
	public void setTicketsCount(Integer ticketsCount) {
		this.ticketsCount = ticketsCount;
	}
	public Double getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(Double ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public LocalDateTime getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}
	public BookingStatus getStatus() {
		return status;
	}
	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BookingResponse [id=" + id + ", eventId=" + eventId + ", eventTitle=" + eventTitle + ", venue=" + venue
				+ ", eventDate=" + eventDate + ", ticketsCount=" + ticketsCount + ", ticketPrice=" + ticketPrice
				+ ", totalAmount=" + totalAmount + ", razorpayOrderId=" + razorpayOrderId + ", bookingDate="
				+ bookingDate + ", status=" + status + "]";
	}
	
	
}
