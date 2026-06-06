package com.eventhub.service;

import java.util.List;

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
import com.eventhub.repository.CategoryRepository;
import com.eventhub.repository.EventRepository;
import com.eventhub.repository.UserRepository;

@Service
public class EventService {

	private final CategoryRepository categoryRepositoy;
	private final UserRepository userRepository;
	private final EventRepository eventRepository;
	
	public EventService(CategoryRepository categoryRepositoy,
						UserRepository userRepository,
						EventRepository eventRepository)
	{
		this.categoryRepositoy=categoryRepositoy;
		this.userRepository=userRepository;
		this.eventRepository=eventRepository;
	}
	
	
//	mapToResponse method
	public EventResponse mapToResponse(Event event)
	{
		return new EventResponse(event.getId(),event.getTitle(),event.getVenue(),event.getEventDate(),
							event.getTicketPrice(),event.getAvailableSeats(),event.getStatus());
	}
	
//	create an event
	public EventResponse createEvent(EventRequest request)
	{
		Category category = categoryRepositoy.findById(request.getCategoryId()).orElseThrow(()->
									new CategoryNotFoundException("Category not found with id : " +request.getCategoryId()));
		User organizer = userRepository.findById(request.getOrganizerId()).orElseThrow(()->
									new UserNotFoundException("User not found with id : "+request.getOrganizerId()));
		
		Event event=new Event();
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
		
		Event savedEvent=eventRepository.save(event);
		return mapToResponse(savedEvent);
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
	public EventResponse updateEvent(Long id,EventRequest request)
	{
		Event event = eventRepository.findById(id).orElseThrow(()->
										new EventNotFoundException("Event Not Found with Id : "+id));
		Category category = categoryRepositoy.findById(request.getCategoryId()).orElseThrow(()->
										new CategoryNotFoundException("Category not found with id : " +request.getCategoryId()));
		User organizer = userRepository.findById(request.getOrganizerId()).orElseThrow(()->
										new UserNotFoundException("User not found with id : "+request.getOrganizerId()));
		
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
		
		 Event updatedEvent=eventRepository.save(event);
		 return mapToResponse(updatedEvent);
	}
	
	
	public void deleteEvent(Long id)
	{
	    Event event = eventRepository.findById(id).orElseThrow(()->
	                    new EventNotFoundException("Event Not Found with Id : " + id));
	    eventRepository.delete(event);
	}
	
	
}
