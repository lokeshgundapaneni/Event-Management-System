package com.eventhub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eventhub.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	List<Event> findByCategoryId(Long id);
	List<Event> findByOrganizerId(Long organizerId);
	
	@Query("SELECT COUNT(b) FROM Booking b WHERE b.event.id = :eventId")
    long countBookingsByEventId(@Param("eventId") Long eventId);
}
