package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.entities.RestaurantTable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantTableService {

    RestaurantTable createRestaurantTable(RestaurantTable restaurantTable);

    RestaurantTable getRestaurantTableByTableId(Integer tableId);

    List<RestaurantTable> getAllRestaurantTablesByRestaurantId(Integer restaurantId);

    List<RestaurantTable> getAllRestaurantTablesByRestaurantName(String restaurantName);

    //List<RestaurantTable> getAllRestaurantTablesByreservationId(Integer reservationId);

    String deleteRestaurantTableByTableId(Integer tableId);

    ResponseEntity<String> deleteAllRestaurantTablesByRestaurantId(Integer restaurantId);


}
