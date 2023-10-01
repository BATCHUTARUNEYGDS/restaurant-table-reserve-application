package com.reserve.restaurantservice.controller;

import com.reserve.restaurantservice.dto.UserValidDTO;
import com.reserve.restaurantservice.entities.Admin;
import com.reserve.restaurantservice.entities.Restaurant;
import com.reserve.restaurantservice.entities.RestaurantManager;
import com.reserve.restaurantservice.entities.User;
import com.reserve.restaurantservice.repository.AdminRepository;
import com.reserve.restaurantservice.repository.RestaurantManagerRepository;
import com.reserve.restaurantservice.repository.RestaurantRepository;
import com.reserve.restaurantservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class ValidController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantRepository restaurantRepository;
    @Autowired
    RestaurantManagerRepository managerRepository;

    @Autowired
    AdminRepository adminRepository;

    @GetMapping("/valid")
    public UserValidDTO getUserEmail() {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        User user = userRepository.findByUserEmailId(username).orElse(null);
        RestaurantManager restaurantManager = managerRepository.findByRestaurantManagerEmailId(username).orElse(null);
        Admin admin = adminRepository.findByAdminEmailId(username).orElse(null);
        if (user != null) {
            return new UserValidDTO(userDetails.getAuthorities().toString(), true, user.getUserId(), user.getUserName(), user.getUserEmailId(), user.getUserPhoneNumber(), user.getUserFullName());
        }
        if (admin != null) {
            return new UserValidDTO(userDetails.getAuthorities().toString(), true, admin.getAdminId(), admin.getAdminUserName(), admin.getAdminEmailId(), null, admin.getAdminFullName());
        } else {
            Restaurant restaurant = restaurantRepository.findByRestaurantManager(restaurantManager).orElse(null);
            return new UserValidDTO(userDetails.getAuthorities().toString(), true, restaurantManager.getRestaurantManagerId(), restaurantManager.getRestaurantManagerUserName(), restaurantManager.getRestaurantManagerEmailId(), restaurant.getRestaurantContactNumber(), restaurantManager.getRestaurantManagerFullName());
        }

    }
}
