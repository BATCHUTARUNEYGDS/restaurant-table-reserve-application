package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.dto.RestaurantTypeDto;
import com.reserve.restaurantservice.entities.RestaurantType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantTypeService {

    RestaurantType getRestaurantTypeById(Integer restaurantTypeId);

    List<RestaurantType> getAllRestaurantTypes();

    RestaurantType createRestaurantType(RestaurantType restaurantType);

    RestaurantType getRestaurantTypeByCuisineName(String cuisine);

    RestaurantType updateRestaurantType(String cuisine, RestaurantType restaurantType);

    List<String> getAllRestaurantTypeNames();

    List<RestaurantTypeDto> getCuisineObjects();

    ResponseEntity<String> deleteCuisine(String cuisine);
}
