package com.eventhub.controller;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dto.request.BookingRequest;
import com.eventhub.dto.response.BookingResponse;
import com.eventhub.service.BookingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("bookings")
@CrossOrigin(origins = "http://localhost:3000")
public class BookingController {

	private final BookingService bookingService;
	
	public BookingController(BookingService bookingService)
	{
		this.bookingService = bookingService;
	}
	
	@PostMapping
	public BookingResponse createBooking(@Valid @RequestBody BookingRequest request)
	{
		return bookingService.createBooking(request);
	}

	@PostMapping("/verify")
	public BookingResponse verifyPayment(@RequestBody Map<String, String> paymentData)
	{
		return bookingService.verifyPayment(paymentData);
	}
	
	@GetMapping
	public List<BookingResponse> getMyBookings()
	{
		return bookingService.getMyBookings();
	}
	
	@GetMapping("/all")
	public List<BookingResponse> getAllBookings()
	{
		return bookingService.getAllBookings();
	}
	
	@GetMapping("/{id}")
	public BookingResponse getBookingById(@PathVariable Long id)
	{
		return bookingService.getBookingById(id);
	}
	
	@PutMapping("/{id}")
	public BookingResponse cancelBooking(@PathVariable Long id)
	{
		return bookingService.cancelBooking(id);
	}
	
	
	@PostMapping("/verify-redirect")
	public ResponseEntity<Void> verifyPaymentRedirect(
	        @RequestParam("razorpay_order_id") String razorpayOrderId,
	        @RequestParam("razorpay_payment_id") String razorpayPaymentId,
	        @RequestParam("razorpay_signature") String razorpaySignature) {
	    
	    try {
	        java.util.Map<String, String> paymentData = new java.util.HashMap<>();
	        
	        // ⚠️ FIX: Keep the exact snake_case strings that the Service layer reads below
	        paymentData.put("razorpay_order_id", razorpayOrderId);
	        paymentData.put("razorpay_payment_id", razorpayPaymentId);
	        paymentData.put("razorpay_signature", razorpaySignature);
	        
	        bookingService.verifyPayment(paymentData);
	        
	        // Redirect back to your local React runtime application on success
	        return ResponseEntity.status(org.springframework.http.HttpStatus.FOUND)
	                .location(java.net.URI.create("http://localhost:3000/bookings?status=success")) 
	                .build();
	                
	    } catch (Exception e) {
	        System.err.println("❌ VERIFICATION FAILED: " + e.getMessage());
	        e.printStackTrace();

	        // Redirect back to your local React runtime application on failure
	        return ResponseEntity.status(org.springframework.http.HttpStatus.FOUND)
	                .location(java.net.URI.create("http://localhost:3000/bookings?status=failed"))
	                .build();
	    }
	}
}