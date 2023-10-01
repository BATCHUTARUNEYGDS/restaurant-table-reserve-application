package com.reserve.restaurantservice.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Entity
//@Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_fk")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_fk")
    private Restaurant restaurant;

    private String status;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalTime timeSlot;

    @NotNull
    private Integer noOfGuests;


    @ManyToMany
    @JoinTable(
            name = "Reservation_Tables",
            joinColumns = @JoinColumn(name = "reservationId"),
            inverseJoinColumns = @JoinColumn(name = "tableId")
    )
    private List<RestaurantTable> reservedRestaurantTables;

    private String guestName;

    private String tableCustomization;

    public Reservation() {
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

    public void assignTableToReservation(RestaurantTable restaurantTable) {
        reservedRestaurantTables.add(restaurantTable);
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
