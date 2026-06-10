package com.eventhub.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dto.request.EventRequest;
import com.eventhub.dto.response.EventResponse;
import com.eventhub.repository.BookingRepository;
import com.eventhub.service.CategoryService;
import com.eventhub.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
@CrossOrigin(origins = "http://localhost:3000")
public class EventController {
	
	private final EventService eventService;
	private final CategoryService categoryService;
	private final BookingRepository bookingRepository;
	
	public EventController(EventService eventService,CategoryService categoryService,BookingRepository bookingRepository)
	{
		this.eventService=eventService;
		this.categoryService=categoryService;
		this.bookingRepository=bookingRepository;
	}
	
	@PostMapping
	public EventResponse createEvent(@Valid @RequestBody EventRequest request)
	{
		return eventService.createEvent(request);
	}
	
	@GetMapping
	public List<EventResponse> getEventByCategory(@RequestParam(required=false) Long categoryId)
	{
		if(categoryId!=null)
		{
			return eventService.getEventsByCategory(categoryId);
		}
	   return eventService.getAllEvents();
	}
	
	
	@GetMapping("/{id}")
	public EventResponse getEventById(@PathVariable Long id)
	{
		return eventService.getEventById(id);
	}
	
	@PutMapping("/{id}")
	public EventResponse updateEvent(@PathVariable Long id,@Valid @RequestBody EventRequest request)
	{
		return eventService.updateEvent(id,request);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEvent(@PathVariable Long id)
	{
		long bookingCount = bookingRepository.countByEventId(id);
	    
	    if (bookingCount > 0) {
	        return ResponseEntity.status(HttpStatus.CONFLICT)
	            .body("Cannot delete event: There are active bookings associated with this event. Please cancel the event instead.");
	    }
	    
	    eventService.deleteEvent(id);
	    return ResponseEntity.ok("Event deleted successfully");
	}
	
	@GetMapping("/organizer/my-events")
    public List<EventResponse> getMyEvents() {
        return eventService.getEventsByOrganizer();
    }

    @GetMapping("/organizer/stats")
    public Map<String, Object> getMyStats() {
        return eventService.getOrganizerStats();
    }
	
}
