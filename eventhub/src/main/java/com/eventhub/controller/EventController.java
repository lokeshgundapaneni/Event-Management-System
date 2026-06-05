package com.eventhub.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventhub.dto.request.EventRequest;
import com.eventhub.entity.Event;
import com.eventhub.service.EventService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/events")
public class EventController {
	
	private final EventService eventService;
	
	public EventController(EventService eventService)
	{
		this.eventService=eventService;
	}
	
	@PostMapping
	public Event createEvent(@Valid @RequestBody EventRequest request)
	{
		return eventService.createEvent(request);
	}
	
	@GetMapping
	public List<Event> getAllEvents()
	{
		return eventService.getAllEvents();
	}
	
	@GetMapping("/{id}")
	public Event getEventById(@PathVariable Long id)
	{
		return eventService.getEventById(id);
	}
	
	@PutMapping("/{id}")
	public Event updateEvent(@PathVariable Long id,@Valid @RequestBody EventRequest request)
	{
		return eventService.updateEvent(id, request);
	}
	
	@DeleteMapping("/{id}")
	public String deleteEvent(@PathVariable Long id)
	{
		eventService.deleteEvent(id);
		return "Event deleted succesfully";
	}
	
}
