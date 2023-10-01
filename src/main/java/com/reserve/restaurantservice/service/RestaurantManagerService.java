package com.reserve.restaurantservice.service;

import com.reserve.restaurantservice.dto.AuthResponseDTO;
import com.reserve.restaurantservice.dto.RestaurantManagerDto;
import com.reserve.restaurantservice.dto.LoginJson;
import com.reserve.restaurantservice.entities.RestaurantManager;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RestaurantManagerService {

    public RestaurantManager createRestaurantManager(RestaurantManagerDto restaurantManagerDto);

    public void deleteRestaurantManager(Integer id);

    public RestaurantManager retrieveRestaurantManager(Integer id);

    public List<RestaurantManagerDto> retrieveAllRestaurantManagerById(Pageable pageable);

    public RestaurantManager updateRestaurantManager(RestaurantManager restaurantManager);


    public ResponseEntity<AuthResponseDTO> restaurantLogin(LoginJson loginJson);



}
