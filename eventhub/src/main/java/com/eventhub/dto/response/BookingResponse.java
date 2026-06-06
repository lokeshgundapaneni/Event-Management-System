package com.eventhub.dto.response;

import java.time.LocalDateTime;

import com.eventhub.enums.BookingStatus;

public class BookingResponse {
	
	private Long id;
    private Integer ticketsCount;
    private Double totalAmount;
    private LocalDateTime bookingDate;
    private BookingStatus status;
	public BookingResponse() {
		super();
	}
	public BookingResponse(Long id, Integer ticketsCount, Double totalAmount, LocalDateTime bookingDate,
			BookingStatus status) {
		super();
		this.id = id;
		this.ticketsCount = ticketsCount;
		this.totalAmount = totalAmount;
		this.bookingDate = bookingDate;
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTicketsCount() {
		return ticketsCount;
	}
	public void setTicketsCount(Integer ticketsCount) {
		this.ticketsCount = ticketsCount;
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
		return "BookingResponse [id=" + id + ", ticketsCount=" + ticketsCount + ", totalAmount=" + totalAmount
				+ ", bookingDate=" + bookingDate + ", status=" + status + "]";
	}
    
    

}
