package com.reserve.restaurantservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ReservationDashboardDto {

    private Integer reservationId;
    private String restaurantName;
    private Integer userId;
    private double restaurantAvgRating;
    private Integer restaurantReviewCount;

    private double userAvgRating;
    private Integer userReviewCount;
    private Integer restaurantId;
    private String status;
    private LocalDate date;
    private LocalTime timeSlot;
    private Integer noOfGuests;
    private List<RestaurantTableDto> reservedRestaurantTables;
    private String guestName;
    private String tableCustomization;

    public ReservationDashboardDto() {
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public double getRestaurantAvgRating() {
        return restaurantAvgRating;
    }

    public void setRestaurantAvgRating(double restaurantAvgRating) {
        this.restaurantAvgRating = restaurantAvgRating;
    }

    public Integer getRestaurantReviewCount() {
        return restaurantReviewCount;
    }

    public void setRestaurantReviewCount(Integer restaurantReviewCount) {
        this.restaurantReviewCount = restaurantReviewCount;
    }

    public double getUserAvgRating() {
        return userAvgRating;
    }

    public void setUserAvgRating(double userAvgRating) {
        this.userAvgRating = userAvgRating;
    }

    public Integer getUserReviewCount() {
        return userReviewCount;
    }

    public void setUserReviewCount(Integer userReviewCount) {
        this.userReviewCount = userReviewCount;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
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

    public List<RestaurantTableDto> getReservedRestaurantTables() {
        return reservedRestaurantTables;
    }

    public void setReservedRestaurantTables(List<RestaurantTableDto> reservedRestaurantTables) {
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

//    public void assignTableToReservation(RestaurantTable restaurantTable) {
//        reservedRestaurantTables.add(restaurantTable);
//    }

    public void setTableCustomization(String tableCustomization) {
        this.tableCustomization = tableCustomization;
    }
}