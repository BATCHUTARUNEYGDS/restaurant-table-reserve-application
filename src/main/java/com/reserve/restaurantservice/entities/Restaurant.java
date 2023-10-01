package com.reserve.restaurantservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantId;

    @Column(unique = true)
    @NotBlank(message = "Restaurant name is required")
    private String restaurantName;

    @Column(unique = true)
    @Email
    @NotBlank(message = "Restaurant mail is mandatory")
    private String restaurantMail;

    @Column(unique = true)
    @NotBlank(message = "mobileNumber is required")
    //@Pattern(regexp = "^(0/91)?[6-9][0-9]{10}$")
    private String restaurantContactNumber;


    @ManyToMany
    @JoinTable(
            name = "Restaurant_CuisineType",
            joinColumns = @JoinColumn(name = "restaurantId"),
            inverseJoinColumns = @JoinColumn(name = "restaurantTypeId")
    )
    private Set<RestaurantType> restaurantCusineType = new HashSet<>();


    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RestaurantRating> rating;

    private Integer approxForTwo;

    private String features;

    @OneToMany(mappedBy = "restaurant")
    @JsonIgnore
    private List<UserRating> userRatings;

    @OneToMany(mappedBy = "theRestaurant")
    @JsonIgnore
    private List<RestaurantTable> restaurantTable = new ArrayList<>();

    private String tag;

    private Double ratingAverage = 0.0;

    private Integer reviewCount = 0;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_location_fk", referencedColumnName = "locationId")
    private RestaurantLocation restaurantLocation;

    private String fullAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_fk")
    private RestaurantManager restaurantManager;

    private String timings;

    private String tableCustomization;

    public Restaurant() {
    }

    public void assignLocation(RestaurantLocation restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

    public Integer getApproxForTwo() {
        return approxForTwo;
    }

    public void setApproxForTwo(Integer approxForTwo) {
        this.approxForTwo = approxForTwo;
    }

    public Integer getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(Integer reviewCount) {
        this.reviewCount = reviewCount;
    }

    public Double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public RestaurantLocation getRestaurantLocation() {
        return restaurantLocation;
    }

    public void setRestaurantLocation(RestaurantLocation restaurantLocation) {
        this.restaurantLocation = restaurantLocation;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RestaurantRating> getRating() {
        return rating;
    }

    public void setRating(List<RestaurantRating> rating) {
        this.rating = rating;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public List<UserRating> getUserRatings() {
        return userRatings;
    }

    public void setUserRatings(List<UserRating> userRatings) {
        this.userRatings = userRatings;
    }

    public List<RestaurantTable> getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(List<RestaurantTable> restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    public RestaurantManager getRestaurantManager() {
        return restaurantManager;
    }

    public void setRestaurantManager(RestaurantManager restaurantManager) {
        this.restaurantManager = restaurantManager;
    }

    public Set<RestaurantType> getRestaurantCusineType() {
        return restaurantCusineType;
    }

    public void setRestaurantCusineType(Set<RestaurantType> restaurantCusineType) {
        this.restaurantCusineType = restaurantCusineType;
    }

    public void setCuisineType(RestaurantType restaurantType) {
        restaurantCusineType.add(restaurantType);
    }

    public void assignRestaurantTable(RestaurantTable oneRestaurantTable) {
        restaurantTable.add(oneRestaurantTable);
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
