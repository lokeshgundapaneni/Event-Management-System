package com.eventhub.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dto.request.BookingRequest;
import com.eventhub.entity.Booking;
import com.eventhub.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("bookings")
public class BookingController {

	private BookingService bookingService;
	
	public BookingController(BookingService bookingService)
	{
		this.bookingService=bookingService;
	}
	
	@PostMapping("/{userId}")
	public Booking createBooking(@PathVariable Long userId,@Valid @RequestBody BookingRequest request)
	{
		return bookingService.createBooking(userId,request);
	}
	
	@GetMapping
	public List<Booking> getAllBookings()
	{
		return bookingService.getAllBookings();
	}
	
	@GetMapping("/{id}")
	public Booking getBookingById(@PathVariable Long id)
	{
		return bookingService.getBookingById(id);
	}
	
	@PutMapping("/{id}")
	public Booking cancelBooking(@PathVariable Long id)
	{
		return bookingService.cancelBooking(id);
	}
}
