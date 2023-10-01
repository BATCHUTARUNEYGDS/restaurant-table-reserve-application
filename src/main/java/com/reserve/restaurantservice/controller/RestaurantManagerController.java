package com.reserve.restaurantservice.controller;

import com.reserve.restaurantservice.dto.RestaurantManagerDto;
import com.reserve.restaurantservice.service.RestaurantManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/restaurantManager")
public class RestaurantManagerController {

    @Autowired
    private RestaurantManagerService restaurantManagerService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/")
    public List<RestaurantManagerDto> retrieveAllRestaurantManagerById(Pageable pageable) {
        return restaurantManagerService.retrieveAllRestaurantManagerById(pageable);
    }

}
