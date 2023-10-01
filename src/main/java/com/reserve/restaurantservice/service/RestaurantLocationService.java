package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.entities.RestaurantLocation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantLocationService {

    RestaurantLocation getRestaurantByLocation(int pincode);

    List<RestaurantLocation> getRestaurantByLocation(String city);

    RestaurantLocation getRestaurantLocationByRestaurantLocationId(Integer restaurantLocationId);

    List<RestaurantLocation> getRestaurantLocations(Pageable pageable);

    RestaurantLocation createRestaurantLocation(RestaurantLocation restaurantLocation);

    RestaurantLocation updateRestaurantLocation(Integer restaurantLocationId, RestaurantLocation restaurantLocation);

    ResponseEntity<String> deleteRestaurantLocation(Integer pincode);


}
