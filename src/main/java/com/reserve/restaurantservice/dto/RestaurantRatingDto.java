package com.reserve.restaurantservice.dto;

import java.util.Date;

public class RestaurantRatingDto {

    private Integer tasteRating;
    private Integer ambienceRating;
    private Integer hospitalityRating;
    private String review;
    private Date reviewDate;
    private String restaurantName;
    private String userName;
    private Integer totalRating;

    public RestaurantRatingDto() {
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

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
    }
}
