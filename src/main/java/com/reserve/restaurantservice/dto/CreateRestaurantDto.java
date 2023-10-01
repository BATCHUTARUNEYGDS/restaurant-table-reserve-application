package com.reserve.restaurantservice.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CreateRestaurantDto {

    private Integer restaurantManagerId;
    private String restaurantName;
    private String restaurantMail;
    private String restaurantContactNumber;
    private String fullAddress;
    private Integer pincode;
    private String description;
    private Integer approxForTwo;
    private String features;
    private String tag;
    private Set<String> restaurantCusineType = new HashSet<>();
    private String timings;
    private String tableCustomization;

    private List<RestaurantTableDto> restaurantTable = new ArrayList<>();

    public Integer getRestaurantManagerId() {
        return restaurantManagerId;
    }

    public void setRestaurantManagerId(Integer restaurantManagerId) {
        this.restaurantManagerId = restaurantManagerId;
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

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
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

    public Set<String> getRestaurantCusineType() {
        return restaurantCusineType;
    }

    public void setRestaurantCusineType(Set<String> restaurantCusineType) {
        this.restaurantCusineType = restaurantCusineType;
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

    public List<RestaurantTableDto> getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(List<RestaurantTableDto> restaurantTable) {
        this.restaurantTable = restaurantTable;
    }
}
