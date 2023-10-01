package com.reserve.restaurantservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;


@Entity
public class RestaurantRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantRatingId;

    //@Size(min = 0, max = 5)
    private Integer tasteRating;

    //@Size(min = 0, max = 5)
    private Integer ambienceRating;

    //@Size(min = 0, max = 5)
    private Integer hospitalityRating;


    private String review;

    //@JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date reviewDate;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurantId_fk")
    private Restaurant restaurant;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_fk")
    private User user;
    private Integer totalRating = 0;

    public RestaurantRating() {
    }

    @PrePersist
    private void onCreate() {
        reviewDate = new Date();
    }

    public Integer getRestaurantRatingId() {
        return restaurantRatingId;
    }

    public void setRestaurantRatingId(Integer restaurantRatingId) {
        this.restaurantRatingId = restaurantRatingId;
    }

    public Integer getTasteRating() {
        return tasteRating;
    }

    public void setTasteRating(Integer tasteRating) {
        this.tasteRating = tasteRating;
    }

    public Integer getAmbienceRating() {
        return ambienceRating;
    }

    public void setAmbienceRating(Integer ambienceRating) {
        this.ambienceRating = ambienceRating;
    }

    public Integer getHospitalityRating() {
        return hospitalityRating;
    }

    public void setHospitalityRating(Integer hospitalityRating) {
        this.hospitalityRating = hospitalityRating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public Integer getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
    }

}
