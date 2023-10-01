package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.dto.CreateRestaurantDto;
import com.reserve.restaurantservice.dto.RestaurantDto;
import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantLocation;
import com.reserve.restaurantservice.entities.RestaurantType;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    List<RestaurantDto> getAllRestaurants(Pageable pageable);

    List<Restaurant> getRestaurants(Pageable pageable);

    Restaurant getRestaurantById(Integer restaurantId);

    Optional<Restaurant> getRestaurantByManager(Integer managerId);

    Restaurant getRestaurantByrestaurantMail(String restaurantMail);

    Restaurant createRestaurant(Integer pincode, Integer managerId, List<String> restaurantTypes, Restaurant restaurant);

    ResponseEntity<String> addRestaurant(CreateRestaurantDto createRestaurantDto);

    ResponseEntity<String> deleteRestaurantById(Integer restaurantId);

    List<Restaurant> getRestaurantByRestaurantTag(String restaurantTag);

    List<Restaurant> getRestaurantByLocation(RestaurantLocation restaurantLocation, Pageable pageable);

    ResponseEntity<String> updateRestaurant(Integer restaurantId, CreateRestaurantDto NewRestaurant);

    List<RestaurantDto> getRestaurantByRestaurantType(RestaurantType restaurantType);

    List<Restaurant> getByCity(String city, Pageable pageable);

    List<Restaurant> getTop3(String city);

    List<RestaurantDto> getRestaurantsByCityAndCuisine(String city, String cuisine);

    List<String> getRestaurantNamesByCity(String city);

}
