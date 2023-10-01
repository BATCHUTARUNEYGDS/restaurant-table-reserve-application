package com.reserve.restaurantservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class UserRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userRatingId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userIdFk")
    @JsonIgnore
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_fk")
    @JsonIgnore
    private Restaurant restaurant;
    private Double punctuality;
    private Double behaviour;
    private Double overAllRating = 0.0;

    private String review;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date reviewDate;

    public UserRating() {
    }

    @PrePersist
    private void onCreate() {
        reviewDate = new Date();
    }

    public Integer getUserRatingId() {
        return userRatingId;
    }

    public void setUserRatingId(Integer userRatingId) {
        this.userRatingId = userRatingId;
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

    public Double getPunctuality() {
        return punctuality;
    }

    public void setPunctuality(Double punctuality) {
        this.punctuality = punctuality;
    }

    public Double getBehaviour() {
        return behaviour;
    }

    public void setBehaviour(Double behaviour) {
        this.behaviour = behaviour;
    }

    public Double getOverAllRating() {
        return overAllRating;
    }

    public void setOverAllRating(Double overAllRating) {
        this.overAllRating = overAllRating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }
}