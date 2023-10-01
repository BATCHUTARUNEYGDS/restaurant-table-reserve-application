package com.reserve.restaurantservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservationDto {

    private Integer restaurantId;
    private Integer userId;
    private String status;
    private LocalDate date;
    private LocalTime timeSlot;
    private Integer noOfGuests;
    private String guestName;
    private String tableCustomization;

    public CreateReservationDto() {
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTimeSlot() {
        return timeSlot;
    }

    public void setTimeSlot(LocalTime timeSlot) {
        this.timeSlot = timeSlot;
    }

    public Integer getNoOfGuests() {
        return noOfGuests;
    }

    public void setNoOfGuests(Integer noOfGuests) {
        this.noOfGuests = noOfGuests;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getTableCustomization() {
        return tableCustomization;
    }

    public void setTableCustomization(String tableCustomization) {
        this.tableCustomization = tableCustomization;
    }
}
