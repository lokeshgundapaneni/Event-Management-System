package com.eventhub.entity;

import java.time.LocalDateTime;

import com.eventhub.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;

@Entity
public class Booking {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Min(value=1,message="minimum one ticket required")
	@Column(nullable=false)
	private Integer ticketsCount;
	
	@Positive
	@Column(nullable=false)
	private Double totalAmount;
	
	private LocalDateTime bookingDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable=false)
	private BookingStatus status;
	

	@Column(name = "razorpay_order_id", unique = true)
	private String razorpayOrderId;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	@JsonBackReference("user-booking")
	private User user;
	
	@ManyToOne
	@JoinColumn(name="event_id")
	@JsonBackReference("event-booking")
	private Event event;

	

	public Booking(Long id, @Min(value = 1, message = "minimum one ticket required") Integer ticketsCount,
			@Positive Double totalAmount, LocalDateTime bookingDate, BookingStatus status, String razorpayOrderId,
			User user, Event event) {
		super();
		this.id = id;
		this.ticketsCount = ticketsCount;
		this.totalAmount = totalAmount;
		this.bookingDate = bookingDate;
		this.status = status;
		this.razorpayOrderId = razorpayOrderId;
		this.user = user;
		this.event = event;
	}

	public Booking() {
		super();
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	
	public String getRazorpayOrderId() {
	    return razorpayOrderId;
	}

	public void setRazorpayOrderId(String razorpayOrderId) {
	    this.razorpayOrderId = razorpayOrderId;
	}
	
	
	@PrePersist
	public void prePersist() {
	    bookingDate = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "Booking [id=" + id + ", ticketsCount=" + ticketsCount + ", totalAmount=" + totalAmount
				+ ", bookingDate=" + bookingDate + ", status=" + status + ", razorpayOrderId=" + razorpayOrderId
				+ ", user=" + user + ", event=" + event + "]";
	}
	
	
	
	
	
}
