package com.reserve.restaurantservice.dto;

import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantTable;
import com.reserve.restaurantservice.entities.User;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationDto {

    @Id
    private Integer reservationId;
    private User user;
    private Restaurant restaurant;
    private String status;
    private LocalDate date;
    private LocalTime timeSlot;
    private Integer noOfGuests;
    private List<RestaurantTable> reservedRestaurantTables;
    private String guestName;
    private String tableCustomization;

    public ReservationDto() {
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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

    public List<RestaurantTable> getReservedRestaurantTables() {
        return reservedRestaurantTables;
    }

    public void setReservedRestaurantTables(List<RestaurantTable> reservedRestaurantTables) {
        this.reservedRestaurantTables = reservedRestaurantTables;
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
