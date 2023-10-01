package com.reserve.restaurantservice.entities;

import javax.persistence.*;

@Entity
public class RatingCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ratingCountId;

    private Integer zeroCount;
    private Integer oneCount;

    private Integer twoCount;

    private Integer threeCount;

    private Integer fourCount;

    @OneToOne
    @JoinColumn(name = "restaurant_fk")
    private Restaurant restaurant;

    private Integer fiveCount;

    public Integer getRatingCountId() {
        return ratingCountId;
    }

    public void setRatingCountId(Integer ratingCountId) {
        this.ratingCountId = ratingCountId;
    }

    public Integer getOneCount() {
        return oneCount;
    }

    public void setOneCount(Integer oneCount) {
        this.oneCount = oneCount;
    }

    public Integer getTwoCount() {
        return twoCount;
    }

    public void setTwoCount(Integer twoCount) {
        this.twoCount = twoCount;
    }

    public Integer getThreeCount() {
        return threeCount;
    }

    public void setThreeCount(Integer threeCount) {
        this.threeCount = threeCount;
    }

    public Integer getFourCount() {
        return fourCount;
    }

    public void setFourCount(Integer fourCount) {
        this.fourCount = fourCount;
    }

    public Integer getFiveCount() {
        return fiveCount;
    }

    public void setFiveCount(Integer fiveCount) {
        this.fiveCount = fiveCount;
    }

    public Integer getZeroCount() {
        return zeroCount;
    }

    public void setZeroCount(Integer zeroCount) {
        this.zeroCount = zeroCount;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
