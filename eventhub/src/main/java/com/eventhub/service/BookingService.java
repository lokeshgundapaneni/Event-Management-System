package com.eventhub.service;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eventhub.dto.request.BookingRequest;
import com.eventhub.dto.response.BookingResponse;
import com.eventhub.entity.Booking;
import com.eventhub.entity.Event;
import com.eventhub.entity.User;
import com.eventhub.enums.BookingStatus;
import com.eventhub.exception.BookingNotFoundException;
import com.eventhub.exception.EventNotFoundException;
import com.eventhub.exception.InsufficientSeatsException;
import com.eventhub.exception.UserNotFoundException;
import com.eventhub.repository.BookingRepository;
import com.eventhub.repository.EventRepository;
import com.eventhub.repository.UserRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;

@Service
public class BookingService {

	@Value("${razorpay.key.id}")
	private String razorpayKeyId;

	@Value("${razorpay.key.secret}")
	private String razorpayKeySecret;

	private final UserRepository userRepository;
	private final EventRepository eventRepository;
	private final BookingRepository bookingRepository;
	
	public BookingService(UserRepository userRepository
						,EventRepository eventRepository
						,BookingRepository bookingRepository)
	{
		this.userRepository=userRepository;
		this.bookingRepository=bookingRepository;
		this.eventRepository=eventRepository;
	}
	
	public BookingResponse mapToResponse(Booking booking)
	{
		Event event = booking.getEvent();

		return new BookingResponse(
				booking.getId(),
				event.getId(),
				event.getTitle(),
				event.getVenue(),
				event.getEventDate(),
				booking.getTicketsCount(),
				event.getTicketPrice(),
				booking.getTotalAmount(),
				booking.getRazorpayOrderId(),
				booking.getBookingDate(),
				booking.getStatus()
		);
	}
	
	public BookingResponse createBooking(BookingRequest request)
	{
		Authentication authentication = SecurityContextHolder
												.getContext()
												.getAuthentication();
		
		String email=authentication.getName();
		
		User user = userRepository.findByEmail(email).orElseThrow(()->
										new UserNotFoundException("User not found"));

		Event event=eventRepository.findById(request.getEventId()).orElseThrow(()->
										new EventNotFoundException("Event not found with id : "+request.getEventId()));
		
		if(event.getRemainingSeats()<request.getTicketsCount())
		{
			throw new InsufficientSeatsException("only "+event.getRemainingSeats()+" seats are available");
		}
		
		double totalAmount=event.getTicketPrice()*request.getTicketsCount();
		String generatedRazorpayOrderId = null;

		try {
			RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpayKeySecret);

			JSONObject orderRequest = new JSONObject();
			orderRequest.put("amount", (int) (totalAmount * 100)); 
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", "rec_booking_" + System.currentTimeMillis());

			Order order = razorpay.orders.create(orderRequest);
			generatedRazorpayOrderId = order.get("id");

		} catch (Exception e) {
			throw new RuntimeException("Failed to initialize transaction with Razorpay Gateway: " + e.getMessage());
		}

		Booking booking=new Booking();
		booking.setEvent(event);
		booking.setUser(user);
		booking.setTicketsCount(request.getTicketsCount());
		booking.setTotalAmount(totalAmount);
		booking.setRazorpayOrderId(generatedRazorpayOrderId);
		booking.setStatus(BookingStatus.PENDING);
		
		Booking savedBooking=bookingRepository.save(booking);
		return mapToResponse(savedBooking);
	}
	
	public BookingResponse verifyPayment(Map<String, String> paymentData) {
		// Match the exact keys sent from your frontend verificationPayload object!
		String razorpayOrderId = paymentData.get("razorpayOrderId");
		String razorpayPaymentId = paymentData.get("razorpayPaymentId");
		String razorpaySignature = paymentData.get("razorpaySignature");

		boolean isValidSignature = false;
		try {
			JSONObject options = new JSONObject();
			// Razorpay's internal SDK math helper utility requires these exact snake_case strings
			options.put("razorpay_order_id", razorpayOrderId);
			options.put("razorpay_payment_id", razorpayPaymentId);
			options.put("razorpay_signature", razorpaySignature);

			isValidSignature = Utils.verifyPaymentSignature(options, razorpayKeySecret);
		} catch (Exception e) {
			throw new RuntimeException("Signature verification computation process failed");
		}

		if (!isValidSignature) {
			throw new RuntimeException("Invalid payment signature payload window detected!");
		}

		Booking booking = bookingRepository.findByRazorpayOrderId(razorpayOrderId)
				.orElseThrow(() -> new BookingNotFoundException("No order matching reference identifier " + razorpayOrderId));

		Event event = booking.getEvent();
		if (event.getRemainingSeats() < booking.getTicketsCount()) {
			throw new InsufficientSeatsException("Seats became unavailable during the payment checkout window.");
		}

		event.setRemainingSeats(event.getRemainingSeats() - booking.getTicketsCount());
		eventRepository.save(event);

		booking.setStatus(BookingStatus.CONFIRMED);
		Booking updatedBooking = bookingRepository.save(booking);

		return mapToResponse(updatedBooking);
	}
	
	public List<BookingResponse> getAllBookings()
	{
		return bookingRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.toList();
	}
	
	public BookingResponse getBookingById(Long id)
	{
		Booking booking= bookingRepository.findById(id).orElseThrow(()->
											new BookingNotFoundException("Booking Not Found With Id : "+id));
		return mapToResponse(booking);
	}
	
	public BookingResponse cancelBooking(Long id)
	{
		Authentication authentication=SecurityContextHolder
												.getContext()
												.getAuthentication();
		String email=authentication.getName();
		Booking booking=bookingRepository.findById(id).orElseThrow(()->
											new BookingNotFoundException("Booking not found with id : "+id));
		
		if(!booking.getUser().getEmail().equals(email))
		{
			throw new RuntimeException(
					"You cannot cancel another user's booking"
			);
		}
		
		if(booking.getStatus()==BookingStatus.CANCELLED)
		{
			throw new RuntimeException("Booking already cancelled");
		}
		Event event=booking.getEvent();
		event.setRemainingSeats(event.getRemainingSeats()+booking.getTicketsCount());
		booking.setStatus(BookingStatus.CANCELLED);
		
		eventRepository.save(event);
		Booking cancelledBooking=bookingRepository.save(booking);
		return mapToResponse(cancelledBooking);
	}
	
	public List<BookingResponse> getMyBookings()
	{
		Authentication authentication =
				SecurityContextHolder
						.getContext()
						.getAuthentication();
		
		String email=authentication.getName();
		
		return bookingRepository
				.findByUserEmail(email)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}
}