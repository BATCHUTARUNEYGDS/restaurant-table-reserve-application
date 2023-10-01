package com.reserve.restaurantservice.dao;

import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantFilter;

import java.util.List;

public interface RestaurantDao {

    List<Restaurant> findRestaurantByFilter(RestaurantFilter restaurantFilter);
}
