package com.eventhub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.eventhub.dto.request.BookingRequest;
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
	
//	create the booking
	public Booking createBooking(Long userid,BookingRequest request)
	{
//		check wheather the user exist or not 
		User user = userRepository.findById(userid).orElseThrow(()->
										new UserNotFoundException("User not found with id : "+userid));
//		check wheather the event exist or not 
		Event event=eventRepository.findById(request.getEventId()).orElseThrow(()->
										new EventNotFoundException("Event not found with id : "+request.getEventId()));
		
		if(event.getRemainingSeats()<request.getTicketsCount())
		{
			throw new InsufficientSeatsException("only "+event.getRemainingSeats()+" seats are available");
		}
		
		double totalAmount=event.getTicketPrice()*request.getTicketsCount();
		event.setRemainingSeats(event.getRemainingSeats()-request.getTicketsCount());
		
		Booking booking=new Booking();
		booking.setEvent(event);
		booking.setUser(user);
		booking.setTicketsCount(request.getTicketsCount());
		booking.setTotalAmount(totalAmount);
		booking.setStatus(BookingStatus.CONFIRMED);
		return bookingRepository.save(booking);
	}
	
//	get all bookings
	public List<Booking> getAllBookings()
	{
		return bookingRepository.findAll();
	}
	
//	get bookings by id
	public Booking getBookingById(Long id)
	{
		return bookingRepository.findById(id).orElseThrow(()->
											new BookingNotFoundException("Booking Not Found With Id : "+id));
	}
	
//	cancel the booking
	public Booking cancelBooking(Long id)
	{
		Booking booking=bookingRepository.findById(id).orElseThrow(()->
											new BookingNotFoundException("Booking not found with id : "+id));
		if(booking.getStatus()==BookingStatus.CANCELLED)
		{
			throw new RuntimeException("Booking already cancelled");
		}
		Event event=booking.getEvent();
		event.setRemainingSeats(event.getRemainingSeats()+booking.getTicketsCount());
		booking.setStatus(BookingStatus.CANCELLED);
		eventRepository.save(event);
		return bookingRepository.save(booking);
	}
}
