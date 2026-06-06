package com.eventhub.service;

import java.util.List;

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

@Service
public class BookingService {

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
		return new BookingResponse(booking.getId(),booking.getTicketsCount(),booking.getTotalAmount(),
							booking.getBookingDate(),booking.getStatus());
	}
	
	
//	create the booking
	public BookingResponse createBooking(BookingRequest request)
	{
		
		Authentication authentication = SecurityContextHolder
                								.getContext()
                								.getAuthentication();
		
		String email=authentication.getName();
		
//		check wheather the user exist or not 
		User user = userRepository.findByEmail(email).orElseThrow(()->
										new UserNotFoundException("User not found"));
//		check wheather the event exist or not 
		Event event=eventRepository.findById(request.getEventId()).orElseThrow(()->
										new EventNotFoundException("Event not found with id : "+request.getEventId()));
		
		if(event.getRemainingSeats()<request.getTicketsCount())
		{
			throw new InsufficientSeatsException("only "+event.getRemainingSeats()+" seats are available");
		}
		
		double totalAmount=event.getTicketPrice()*request.getTicketsCount();
		event.setRemainingSeats(event.getRemainingSeats()-request.getTicketsCount());
		eventRepository.save(event);
		Booking booking=new Booking();
		booking.setEvent(event);
		booking.setUser(user);
		booking.setTicketsCount(request.getTicketsCount());
		booking.setTotalAmount(totalAmount);
		booking.setStatus(BookingStatus.CONFIRMED);
		Booking savedBooking=bookingRepository.save(booking);
		return mapToResponse(savedBooking);
	}
	
//	get all bookings
	public List<BookingResponse> getAllBookings()
	{
		return bookingRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.toList();
	}
	
//	get bookings by id
	public BookingResponse getBookingById(Long id)
	{
		Booking booking= bookingRepository.findById(id).orElseThrow(()->
											new BookingNotFoundException("Booking Not Found With Id : "+id));
		return mapToResponse(booking);
	}
	
//	cancel the booking
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
	
//	find booking by user
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
