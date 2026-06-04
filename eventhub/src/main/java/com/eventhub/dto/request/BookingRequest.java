package com.eventhub.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class BookingRequest {

    @NotNull(message = "Event id is required")
    private Long eventId;

    @Min(value = 1, message = "Minimum one ticket is required")
    private Integer ticketsCount;

    public BookingRequest() {
    }

    public BookingRequest(Long eventId, Integer ticketsCount) {
        this.eventId = eventId;
        this.ticketsCount = ticketsCount;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Integer getTicketsCount() {
        return ticketsCount;
    }

    public void setTicketsCount(Integer ticketsCount) {
        this.ticketsCount = ticketsCount;
    }
}