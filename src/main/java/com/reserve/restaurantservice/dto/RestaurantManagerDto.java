package com.reserve.restaurantservice.dto;

import com.reserve.restaurantservice.entities.Role;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

public class RestaurantManagerDto {

    @Id
    private Integer restaurantManagerId;
    private String restaurantManagerUserName;
    private String restaurantManagerEmailId;
    private String restaurantManagerPassword;
    private String restaurantManagerFullName;
    private List<Role> roles = new ArrayList<>();

    private Integer pincode;

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }

    public Integer getRestaurantManagerId() {
        return restaurantManagerId;
    }

    public void setRestaurantManagerId(Integer restaurantManagerId) {
        this.restaurantManagerId = restaurantManagerId;
    }

    public String getRestaurantManagerUserName() {
        return restaurantManagerUserName;
    }

    public void setRestaurantManagerUserName(String restaurantManagerUserName) {
        this.restaurantManagerUserName = restaurantManagerUserName;
    }

    public String getRestaurantManagerEmailId() {
        return restaurantManagerEmailId;
    }

    public void setRestaurantManagerEmailId(String restaurantManagerEmailId) {
        this.restaurantManagerEmailId = restaurantManagerEmailId;
    }

    public String getRestaurantManagerPassword() {
        return restaurantManagerPassword;
    }

    public void setRestaurantManagerPassword(String restaurantManagerPassword) {
        this.restaurantManagerPassword = restaurantManagerPassword;
    }

    public String getRestaurantManagerFullName() {
        return restaurantManagerFullName;
    }

    public void setRestaurantManagerFullName(String restaurantManagerFullName) {
        this.restaurantManagerFullName = restaurantManagerFullName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
