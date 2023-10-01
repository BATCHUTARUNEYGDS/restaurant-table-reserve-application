package com.reserve.restaurantservice.dto;

import com.reserve.restaurantservice.entities.RestaurantLocation;
import com.reserve.restaurantservice.entities.RestaurantType;

import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;

public class RestaurantDto {

    @Id
    private Integer restaurantId;
    private String restaurantName;
    private String restaurantMail;
    private String restaurantContactNumber;
    private Set<RestaurantType> restaurantCusineType = new HashSet<>();
    private String description;
    //    private List<RestaurantRating> rating;
    private Integer approxForTwo;
    private String features;
    //    private List<RestaurantTable> restaurantTable = new ArrayList<>();
    private String tag;
    private Double ratingAverage = 0.0;
    private Integer reviewCount = 0;
    private RestaurantLocation restaurantLocation;
    private String fullAddress;
    //    private RestaurantManager restaurantManager;
    private String timings;
    private String tableCustomization;

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantMail() {
        return restaurantMail;
    }

    public void setRestaurantMail(String restaurantMail) {
        this.restaurantMail = restaurantMail;
    }

    public String getRestaurantContactNumber() {
        return restaurantContactNumber;
    }

    public void setRestaurantContactNumber(String restaurantContactNumber) {
        this.restaurantContactNumber = restaurantContactNumber;
    }

    public Set<RestaurantType> getRestaurantCusineType() {
        return restaurantCusineType;
    }

    public void setRestaurantCusineType(Set<RestaurantType> restaurantCusineType) {
        this.restaurantCusineType = restaurantCusineType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public Integer getApproxForTwo() {
        return approxForTwo;
    }

    public void setApproxForTwo(Integer approxForTwo) {
        this.approxForTwo = approxForTwo;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public RestaurantLocation getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(RestaurantLocation restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }


    public String getTimings() {
        return timings;
    }

    public void setTimings(String timings) {
        this.timings = timings;
    }

    public String getTableCustomization() {
        return tableCustomization;
    }

    public void setTableCustomization(String tableCustomization) {
        this.tableCustomization = tableCustomization;
    }
}
