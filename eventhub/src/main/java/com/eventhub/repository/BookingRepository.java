package com.eventhub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.eventhub.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByUserEmail(String email);

	Optional<Booking> findByRazorpayOrderId(String razorpayOrderId);

	long countByEventId(Long id);

	@Query("SELECT SUM(b.totalAmount) FROM Booking b WHERE b.event.id = :eventId AND b.status = 'CONFIRMED'")
	Double findTotalRevenueByEventId(@Param("eventId") Long eventId);
}