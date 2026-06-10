package com.eventhub.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.eventhub.dto.request.EventRequest;
import com.eventhub.dto.response.EventResponse;
import com.eventhub.entity.Category;
import com.eventhub.entity.Event;
import com.eventhub.entity.User;
import com.eventhub.enums.EventStatus;
import com.eventhub.exception.CategoryNotFoundException;
import com.eventhub.exception.EventNotFoundException;
import com.eventhub.exception.UserNotFoundException;
import com.eventhub.repository.BookingRepository;
import com.eventhub.repository.CategoryRepository;
import com.eventhub.repository.EventRepository;
import com.eventhub.repository.UserRepository;

@Service
public class EventService {

	private final CategoryRepository categoryRepositoy;
	private final UserRepository userRepository;
	private final EventRepository eventRepository;
	private final BookingRepository bookingRepository;
	
	public EventService(CategoryRepository categoryRepositoy,
						UserRepository userRepository,
						EventRepository eventRepository,
						BookingRepository bookingRepository)
	{
		this.categoryRepositoy=categoryRepositoy;
		this.userRepository=userRepository;
		this.eventRepository=eventRepository;
		this.bookingRepository=bookingRepository;
	}
	
	
//	mapToResponse method
	public EventResponse mapToResponse(Event event)
	{
		return new EventResponse(
	            event.getId(),
	            event.getTitle(),
	            event.getDescription(),
	            event.getVenue(),
	            event.getEventDate(),
	            event.getTicketPrice(),
	            event.getAvailableSeats(),
	            event.getRemainingSeats(),
	            event.getImageUrl(),
	            event.getStatus(),
	            event.getCategory().getName(),
	            event.getOrganizer().getName()
	    );
	}
	
//	create an event
	public EventResponse createEvent(EventRequest request) {
        // Automatically identify user from JWT context
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); 
        User organizer = userRepository.findByEmail(email)
            .orElseThrow(() -> new UserNotFoundException("Organizer not found with email: " + email));

        Category category = categoryRepositoy.findById(request.getCategoryId())
            .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + request.getCategoryId()));
        
        Event event = new Event();
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setVenue(request.getVenue());
        event.setEventDate(request.getEventDate());
        event.setTicketPrice(request.getTicketPrice());
        event.setAvailableSeats(request.getAvailableSeats());
        event.setRemainingSeats(request.getAvailableSeats());
        event.setImageUrl(request.getImageUrl());
        event.setStatus(EventStatus.PENDING);
        event.setCategory(category);
        event.setOrganizer(organizer);
        
        return mapToResponse(eventRepository.save(event));
    }
	
//	get events by category id
	public List<EventResponse> getEventsByCategory(Long id)
	{
		return  eventRepository.findByCategoryId(id)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}
	
//	fetch all the entities
	public List<EventResponse> getAllEvents()
	{
		return eventRepository.findAll()
				.stream()
				.map(this::mapToResponse)
				.toList();
	}
	
//	fetch by id
	public EventResponse getEventById(Long id)
	{
		Event event = eventRepository.findById(id).orElseThrow(()->
												new EventNotFoundException("Event Not Found with Id : "+id));
		return mapToResponse(event);
	}
	
//	public update the Event
	public EventResponse updateEvent(Long id, EventRequest request) {
        Event event = eventRepository.findById(id)
            .orElseThrow(() -> new EventNotFoundException("Event Not Found with Id: " + id));
        
        Category category = categoryRepositoy.findById(request.getCategoryId())
            .orElseThrow(() -> new CategoryNotFoundException("Category not found with id: " + request.getCategoryId()));
        
        event.setTitle(request.getTitle());
        event.setDescription(request.getDescription());
        event.setVenue(request.getVenue());
        event.setEventDate(request.getEventDate());
        event.setTicketPrice(request.getTicketPrice());
        event.setAvailableSeats(request.getAvailableSeats());
        event.setRemainingSeats(request.getAvailableSeats());
        event.setImageUrl(request.getImageUrl());
        event.setCategory(category);
        
        return mapToResponse(eventRepository.save(event));
    }
	
	
	public void deleteEvent(Long id)
	{
		long bookingCount = bookingRepository.countByEventId(id);
		Event event = eventRepository.findById(id).orElseThrow(()->
        										new EventNotFoundException("Event Not Found with Id : " + id));
		if (bookingCount > 0) {
	        throw new RuntimeException("Cannot delete event: There are " + bookingCount + " bookings associated with this event.");
	    }
	    eventRepository.delete(event);
	}
	
	
	
	// In EventService.java
	public List<EventResponse> getEventsByOrganizer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow();
        return eventRepository.findByOrganizerId(user.getId()).stream()
                .map(this::mapToResponse).toList();
    }

	public Map<String, Object> getOrganizerStats() {
	    Long userId = getLoggedInUserId();
	    List<Event> events = eventRepository.findByOrganizerId(userId);
	    
	    long totalEvents = events.size();
	    
	    // Calculate total revenue from CONFIRMED bookings only
	    double totalRevenue = events.stream()
	        .mapToDouble(e -> {
	            Double revenue = bookingRepository.findTotalRevenueByEventId(e.getId());
	            return revenue != null ? revenue : 0.0; // If null (no confirmed bookings), return 0
	        })
	        .sum();

	    Map<String, Object> stats = new HashMap<>();
	    stats.put("totalEvents", totalEvents);
	    stats.put("totalRevenue", totalRevenue);
	    return stats;
	}


	private Long getLoggedInUserId() {
	    // 1. Get the authenticated user's email (the "Principal")
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    String email = authentication.getName();
	    
	    // 2. Find the user in the database to get their actual ID
	    User user = userRepository.findByEmail(email)
	            .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
	            
	    return user.getId();
	}
	
	
}
