package com.reserve.restaurantservice.entities;

public class ReservationDecline {

    private Integer reservationId;

    private String message;

    public ReservationDecline() {
    }

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
