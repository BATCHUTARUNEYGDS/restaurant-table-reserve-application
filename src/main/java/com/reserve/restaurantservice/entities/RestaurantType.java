package com.reserve.restaurantservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class RestaurantType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer restaurantTypeId;

    private String cuisine;

    @JsonIgnore
    @ManyToMany(mappedBy = "restaurantCusineType")
    private List<Restaurant> restaurant;

    public RestaurantType() {
    }

    public Integer getRestaurantTypeId() {
        return restaurantTypeId;
    }

    public void setRestaurantTypeId(Integer restaurantTypeId) {
        this.restaurantTypeId = restaurantTypeId;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public List<Restaurant> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(List<Restaurant> restaurant) {
        this.restaurant = restaurant;
    }
}
